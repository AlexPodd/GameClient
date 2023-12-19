package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;

public class EnterTheGameScreen extends ScreenAdapter {
    private MyGame game;
    private Texture BackGround;
    private Stage stage;
    private int screenWidth;
    private int screenHeight;
    private TextField textField;
    private String RoomName;


    public EnterTheGameScreen(MyGame game){
        BackGround = new Texture("BackGround.png");
        stage = new Stage();
        this.game = game;
        this.screenHeight = game.screenHeight;
        this.screenWidth = game.screenWidth;
        CreateEnterTheGameScreen();
    }
    public void CreateEnterTheGameScreen(){
        final TextButton StartGame = new TextButton("Create New Game",game.skin);
        StartGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDialog();
                //game.setScreen(new GameScreen(game));
            }
        });

        TextButton EnterGame = new TextButton("Enter game",game.skin);
        EnterGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDialogCon();
            }
        });
        Table table = new Table(game.skin);
        table.setSize(screenWidth,screenHeight/2);
        table.add(StartGame).width(screenWidth/4).height(screenHeight/7);
        table.row().pad(10);
        table.add(EnterGame).width(screenWidth/4).height(screenHeight/7);
        stage.addActor(table);
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
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


    private void showDialog() {
        Dialog dialog = new Dialog("Enter Room Name", game.skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    RoomName = textField.getText();
                    game.setScreen(new WaitingCreateScreen(game,RoomName));
                }
            }
        };

        textField = new TextField("", game.skin);
        dialog.getContentTable().add(textField).width(game.screenWidth/4);

        dialog.button("Enter", true);

        dialog.button("Cancel", false);

        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);

        dialog.show(stage);
    }

    private void showDialogCon() {
        Dialog dialog = new Dialog("Enter Room Name", game.skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    RoomName = textField.getText();
                    game.setScreen(new WaitingConnectScreen(game,RoomName));

                }
            }
        };

        textField = new TextField("", game.skin);
        dialog.getContentTable().add(textField).width(game.screenWidth/4);

        dialog.button("Enter", true);

        dialog.button("Cancel", false);

        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);

        dialog.show(stage);
    }
}
