package com.mygdx.game.ServernayaChast;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnection {
    private static Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;
    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int Port) throws IOException{
        this(eventListener, new Socket(ipAddr, Port));
    }

    public TCPConnection(final TCPConnectionListener eventListener, Socket socket) throws IOException {
        TCPConnection.socket = socket;
        this.eventListener = eventListener;
        //InputStreamReader считывает символы из байтового входного потока.
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while(!rxThread.isInterrupted()){
                        String mag = in.readLine();
                        eventListener.onReceiveString(TCPConnection.this, mag);
                    }
                } catch (IOException e) {
                    eventListener.onException(TCPConnection.this, e);
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }
    public synchronized void sendString(String value){
        try {
            out.write(value+"\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }
    public synchronized void disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        }
        catch (IOException e){
            eventListener.onException(TCPConnection.this, e);
        }
    }
    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString(){
        return socket.getInetAddress()+" "+ socket.getPort();
    }
    public InetAddress getIP(){
        return socket.getLocalAddress();
    }
    public int getPort(){
        return socket.getLocalPort();
    }
}

