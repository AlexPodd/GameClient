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
    private Image heartImage, heartImage1, heartImage2;

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
        heartImage1 = new Image(FullHeart);
        heartImage2 = new Image(FullHeart);
        // Установка позиции сердечка на экране
        heartImage.setPosition(0, 0);
        heartImage1.setPosition(64, 0);
        heartImage2.setPosition(128, 0);

        // Добавление сердечка на сцену
        stage.addActor(heartImage);
        stage.addActor(heartImage1);
        stage.addActor(heartImage2);
    }

    public void draw() {
        stage.draw();
    }
    public void UpdateHud(float Healt){
        if(Healt == 6.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage1 = new Image(FullHeart);
            heartImage2 = new Image(FullHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 5.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage1 = new Image(FullHeart);
            heartImage2 = new Image(Halfheart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 4.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage1 = new Image(FullHeart);
            heartImage2 = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 3.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage1 = new Image(Halfheart);
            heartImage2 = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 2.0){
            stage.clear();
            heartImage = new Image(FullHeart);
            heartImage1 = new Image(EmptyHeart);
            heartImage2 = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 1.0){
            stage.clear();
            heartImage = new Image(Halfheart);
            heartImage1 = new Image(EmptyHeart);
            heartImage2 = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
        if(Healt == 0.0){
            stage.clear();
            heartImage = new Image(EmptyHeart);
            heartImage1 = new Image(EmptyHeart);
            heartImage2 = new Image(EmptyHeart);
            heartImage.setPosition(0, 0);
            heartImage1.setPosition(64, 0);
            heartImage2.setPosition(128, 0);
            stage.addActor(heartImage);
            stage.addActor(heartImage1);
            stage.addActor(heartImage2);
            return;
        }
    }
}