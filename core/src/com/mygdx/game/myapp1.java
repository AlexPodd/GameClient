package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameState.InGame;
import com.mygdx.game.ServernayaChast.UDPClient;
import com.mygdx.game.entity.Player;
import com.mygdx.game.input.input;
import com.mygdx.game.map.LvlManager;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class myapp1 extends ApplicationAdapter {
	private SpriteBatch batch;

	private Stage stage;
	public int GameState;
	private InGame game;

	private Skin skin;
	private Texture BackGround;
	int screenWidth;
	int screenHeight;
	Viewport viewport;

	@Override
	public void create() {
		screenWidth = Gdx.graphics.getBackBufferWidth();
		screenHeight =  Gdx.graphics.getBackBufferHeight();



        BackGround = new Texture("BackGround.png");
		GameState = 0;
		batch = new SpriteBatch();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


		MainMenuStage();
	}
	public void MainMenuStage(){
		TextButton startButton = new TextButton("Start New Game", skin);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game = new InGame();
				game.Create();
				GameState = 1;

			}
		});
		TextButton exitButton = new TextButton("Exit", skin);

		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Действия при нажатии кнопки "Выход"
				Gdx.app.exit();
			}
		});
		TextButton optionsButton = new TextButton("Options", skin);
		optionsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//stage.clear();

				resize(1920,1080);
			}
		});
		Table table = new Table(skin);
		table.setSize(screenWidth,screenHeight/2);
		table.add(startButton).width(screenWidth/4).height(screenHeight/7);
		table.row();
		table.add(optionsButton).width(screenWidth/4).height(screenHeight/7);
		table.row();
		table.add(exitButton).width(screenWidth/4).height(screenHeight/7);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		if(GameState == 0) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			ScreenUtils.clear(1, 1, 1, 1);


			batch.begin();
			// Отрисовка фона или других элементов главного меню
			batch.draw(BackGround,0,0,screenWidth,screenHeight);

			batch.end();

			stage.act(Gdx.graphics.getDeltaTime());
			//stage.setDebugAll(true);
			stage.draw();
		}
		if (GameState == 1){
		game.render();
		}
	}

	@Override
	public void dispose() {

		skin.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		screenWidth = width;
		screenHeight = height;

	}
}
