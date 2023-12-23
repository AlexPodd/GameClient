package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;

public class MainMenuScreen extends ScreenAdapter {
    private MyGame game;
    private Texture BackGround;
    private Stage stage;
    public MainMenuScreen(MyGame game){
    this.game = game;

    if(game.screenWidth == 1920){
        BackGround = new Texture("BackGroundFULLHD.png");
    }
    else {
        BackGround = new Texture("BackGround.png");
    }
        stage = new Stage();
        MainMenuStage();
    }
    public void MainMenuStage(){
        TextButton startButton = new TextButton("Start New Game", game.skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new EnterTheGameScreen(game));
            }
        });
        TextButton exitButton = new TextButton("Exit", game.skin);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Действия при нажатии кнопки "Выход"
                Gdx.app.exit();
            }
        });
        TextButton optionsButton = new TextButton("Options", game.skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
            }
        });
        Table table = new Table(game.skin);
        table.setSize(game.screenWidth,game.screenHeight/2);
        table.add(startButton).width(game.screenWidth/4).height(game.screenHeight/7);
        table.row();
        table.add(optionsButton).width(game.screenWidth/4).height(game.screenHeight/7);
        table.row();
        table.add(exitButton).width(game.screenWidth/4).height(game.screenHeight/7);
        stage.addActor(table);


    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if(width == 1920){
            BackGround = new Texture("BackGroundFULLHD.png");
        }
        else {
            BackGround = new Texture("BackGround.png");
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();

        game.batch.draw(BackGround,0,0,game.screenWidth,game.screenHeight);

        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        //stage.setDebugAll(true);
        stage.draw();
    }
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        dispose();
    }
}
