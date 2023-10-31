package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.entity.Player;
import com.mygdx.game.input.input;
import com.mygdx.game.map.LvlManager;

import javax.swing.plaf.nimbus.State;

public class myapp extends ApplicationAdapter {
	private SpriteBatch batch;

	private Player player;
	private Player player1;

	private input inputProcessor;

	public int TileSize;
	private Texture PlayerText;
	private LvlManager lvlManager;
	public static final int WIDTH = 400, HEIGHT = 300;

	private int[][] map;
	private float Time;
	private float StateTime;

	public OrthographicCamera camera;
	@Override
	public void create () {
		PlayerText = new Texture("character.png");
		lvlManager = new LvlManager();
		lvlManager.MapCreate();
		TileSize = 32;
		batch = new SpriteBatch();
		player = new Player(0,0,0,0,0,1, PlayerText);
		inputProcessor = new input(player);
		Gdx.input.setInputProcessor(inputProcessor);
		camera = new OrthographicCamera(WIDTH, HEIGHT);    // 6
		camera.position.set(WIDTH / 2, HEIGHT / 2, 0);

		player1 = new Player(0,0,0,0,0,1, PlayerText);

		StateTime = 0F;
	}

	@Override
	public void render () {
		StateTime += Gdx.graphics.getDeltaTime();
		//System.out.println(StateTime);
		ScreenUtils.clear(1, 1, 1, 1);

		batch.begin();
		lvlManager.MapUpdate(batch);


		player.MoveTo(inputProcessor.getDir());

		camera.position.set(player.getX(), player.getY(),0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

         player1.PosInter(new Vector2(0,0),new Vector2(10,100),StateTime, 10F, 10);

      player1.render(batch);


 		player.render(batch);








		batch.end();


	}

	@Override
	public void dispose () {

		batch.dispose();
	}

}
