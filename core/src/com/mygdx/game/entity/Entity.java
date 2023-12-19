package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.sql.Timestamp;
import java.util.Vector;

public abstract class Entity {

    protected Vector2 pos = new Vector2();

    protected float MoveSpeed;
    protected float AttackSpeed;

    protected float HP;
    protected float Damage;

    public Entity(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed){
        this.HP = HP;
        this.pos.set(x,y);
        this.Damage = Damage;
        this.AttackSpeed = AttackSpeed;
        this.MoveSpeed = MoveSpeed;
    }

    public class ObjectInterpolator {
        private float startX, startY; // Начальные координаты объекта
        private float endX, endY;     // Конечные координаты объекта
        private float duration;        // Длительность интерполяции в секундах
        private float currentTime;     // Текущее время интерполяции
        private boolean isInterpolating; // Флаг, указывающий, идет ли в данный момент интерполяция
        private float delta;
        public ObjectInterpolator(float startX, float startY, float endX, float endY, Timestamp StartTs, Timestamp EndTs, float delta) {
            this.startX = startX;
            this.delta = delta;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            if (StartTs.equals(EndTs)){
                duration = 0.01F;
            }
            else {
                duration = EndTs.getTime() - StartTs.getTime();
            }
            this.currentTime = 0f;
            this.isInterpolating = false;
        }

        public void startInterpolation() {
            isInterpolating = true;
            currentTime = 0f;
        }
        public void update() {
            while (isInterpolating){
                currentTime += delta;
                if (currentTime >= duration) {
                    currentTime = duration;
                    isInterpolating = false;
                }

                float progress = currentTime / duration;
                // Используем метод интерполяции для вычисления текущих координат
                float interpolatedX = Interpolation.smoother.apply(startX, endX, progress);
                float interpolatedY = Interpolation.smoother.apply(startY, endY, progress);

                // Используйте полученные координаты для обновления положения вашего объекта
                updateObjectPosition(interpolatedX, interpolatedY);
            }

        }

        private void updateObjectPosition(float x, float y) {
            // Здесь обновите положение вашего объекта (например, установите новые координаты)

            MoveTo(new Vector2(x-getX(), y-getY()));

        }
    }

}
