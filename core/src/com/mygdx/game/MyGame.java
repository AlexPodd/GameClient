package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.ServernayaChast.TCPClient;
import com.mygdx.game.ServernayaChast.UDPClient;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MyGame extends Game {
    private static TCPClient tcpClient;

    private static UDPClient udpClient;
    public MyGame(String IP, int TCPPort, int UdpPort){
        tcpClient = new TCPClient(IP, TCPPort, this);
        try {
            udpClient = new UDPClient(UdpPort, IP, new DatagramSocket());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public static Batch batch;
    public static Skin skin;
    public int screenWidth;
    public int screenHeight;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));




        setScreen(new MainMenuScreen(this));
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }
    public UDPClient getUdpClient() {
        return udpClient;
    }
}
