package com.mygdx.game.ServernayaChast;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.EnterTheGameScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class TCPClient implements TCPConnectionListener {
    private TCPConnection connection;
    private MyGame game;
    private InetAddress myIP;
    private final int myPort;
    public TCPClient(String IP, int Port, MyGame game){
        this.game = game;
     try {
        connection = new TCPConnection(this, IP, Port);
    } catch (IOException e) {
        System.out.println("Connection exception "+ e);
    }
     this.myPort = connection.getPort();
        URL url = null;
        try {
            url = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String ipAddress = br.readLine().trim();
            this.myIP = InetAddress.getByName(ipAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}

    @Override
    public void onConnectionReady(TCPConnection topConnection) {
    }

    @Override
    public void onReceiveString(TCPConnection topConnection, String values) {

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
            PrepareForGame prepareForGame = PrepareForGame.getInstance();
            prepareForGame.setEnemies(values);
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

    public void close(){
        connection.close();
    }
}
