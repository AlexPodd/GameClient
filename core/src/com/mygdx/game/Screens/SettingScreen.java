package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.MyGame;

public class SettingScreen extends ScreenAdapter {
    private MyGame game;
    private Skin skin;
    private Stage stage;
    private int screenWidth;
    private int screenHeight;
    private Texture BackGround;

    private OrthographicCamera camera;
    private Image image;
    public SettingScreen(MyGame game){
        this.game = game;
        this.skin = game.skin;
        this.stage = new Stage();
        this.screenHeight = game.screenHeight;
        this.screenWidth = game.screenWidth;
        BackGround = new Texture("BackGround.png");
        image = new Image(BackGround);
        SettingStage();

    }
    public void SettingStage(){
        TextButton res1 = new TextButton("1280/720",skin);
        res1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(1280, 720);
                game.screenHeight = 720;
                game.screenWidth = 1280;
                game.setScreen(new SettingScreen(game));
            }
        });

        TextButton res2 = new TextButton("1920/1080",skin);
        res2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(1920, 1080);
                game.screenHeight = 1080;
                game.screenWidth = 1920;
                game.setScreen(new SettingScreen(game));
            }
        });

        TextButton FullScreen = new TextButton("FullScreen",skin);
        FullScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

            }
        });

        TextButton Windowed = new TextButton("Windowed",skin);
        Windowed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(game.screenWidth, game.screenHeight);

            }
        });
        TextButton exit = new TextButton("Back", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));

            }
        });

        Table table = new Table(skin);
        table.setSize(screenWidth,screenHeight/2);
        table.add(res1).width(screenWidth/4).height(screenHeight/7);
        table.add().pad(10);
        table.add(res2).width(screenWidth/4).height(screenHeight/7);
        table.row().pad(10);
        table.add(FullScreen).width(screenWidth/4).height(screenHeight/7);
        table.add().pad(10);
        table.add(Windowed).width(screenWidth/4).height(screenHeight/7);
        table.row().pad(10);
        table.add().pad(50);
        table.add(exit).width(screenWidth/4).height(screenHeight/7);
        stage.addActor(table);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
