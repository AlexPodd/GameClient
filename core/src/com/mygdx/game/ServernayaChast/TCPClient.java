package com.mygdx.game.ServernayaChast;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.EnterTheGameScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;

import java.io.IOException;
import java.net.InetAddress;

public class TCPClient implements TCPConnectionListener {
    private TCPConnection connection;
    private MyGame game;
    private final InetAddress myIP;
    private final int myPort;
    public TCPClient(String IP, int Port, MyGame game){
        this.game = game;
     try {
        connection = new TCPConnection(this, IP, Port);
    } catch (IOException e) {
        System.out.println("Connection exception "+ e);
    }
     this.myIP = connection.getIP();
     this.myPort = connection.getPort();
}

    public InetAddress getMyIP() {
        return myIP;
    }

    public int getMyPort() {
        return myPort;
    }

    @Override
    public void onConnectionReady(TCPConnection topConnection) {
    }

    @Override
    public void onReceiveString(TCPConnection topConnection, String values) {
        System.out.println(values);
        String[] Words = values.split(" ");
        if(values.equals("NameIsBusy") || values.equals("NameIsNotFound")){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new EnterTheGameScreen(game));
                }
            });
        }
        if(Words[0].equals("E")){
            try {
                game.getUdpClient().SendConnectMessage("Con "+myIP+":"+myPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(Words[0].equals("StartNewGame")){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameScreen(game));
                }
            });
        }

    }

    @Override
    public void onDisconnect(TCPConnection topConnection) {
    }

    @Override
    public void onException(TCPConnection topConnection, Exception e) {

    }
    public void SendMessage(String value){
        connection.sendString(value);
    }

}
