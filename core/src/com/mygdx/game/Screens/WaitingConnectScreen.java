package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;

public class WaitingConnectScreen extends ScreenAdapter {
    private MyGame game;
    private Texture BackGround;
    public WaitingConnectScreen(MyGame game, String RoomName){
        this.game = game;
        BackGround = new Texture("BackGround.png");
        game.getTcpClient().SendMessage("ConnectToRoom "+RoomName);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(BackGround,0,0,game.screenWidth,game.screenHeight);
        game.batch.end();
    }
}
