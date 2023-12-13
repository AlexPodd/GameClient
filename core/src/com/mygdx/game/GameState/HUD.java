package com.mygdx.game.GameState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
    private Stage stage;
    private Image heartImage;

    private Texture Halfheart;
    private Texture FullHeart;

    private Texture EmptyHeart;

    public HUD(Viewport viewport, Batch batch) {
        stage = new Stage(viewport, batch);

        // Загрузка текстуры сердечка

        FullHeart = new Texture("сердце1.png");
        Halfheart = new Texture("сердце2.png");
        EmptyHeart = new Texture("сердце3.png");
        heartImage = new Image(FullHeart);
        // Установка позиции сердечка на экране
        heartImage.setPosition(0, 0);

        // Добавление сердечка на сцену
        stage.addActor(heartImage);
    }

    public void draw() {
        stage.draw();
    }
    public void UpdateHud(float Healt){
        if (Healt == 2.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage.setPosition(0, 0);
            stage.addActor(heartImage);
            return;
        }
        if (Healt == 1.0){
            stage.clear();
            heartImage = new Image(Halfheart);
            heartImage.setPosition(0, 0);
            stage.addActor(heartImage);
            return;
        }
        else {
            stage.clear();
            heartImage = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            stage.addActor(heartImage);
        }
    }
}