package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.sql.Timestamp;

public abstract class Enemy extends Entity{
    protected Texture texture;
    protected final int width = 16;
    protected final int hight = 16;
    protected boolean IsMovingLeft;
    protected boolean IsMovingRight;
    protected boolean IsMovingUp;
    protected boolean IsMovingDown;
    protected boolean IsStaying;
    protected TextureRegion[][] EnemyAnim;
    protected Vector2 pos = new Vector2();
    protected float stateTime;

    protected Animation RightwalkAnimation, UpwalkAnimation, DownwalkAnimation, LeftwalkAnimation;
    public Enemy(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);

    }
    protected void CreateAnim(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                EnemyAnim[i][j] = new TextureRegion(texture, i * width, j * hight, width, hight);
            }
        }
        TextureRegion[] right = new TextureRegion[4];
        right[0] = EnemyAnim[3][0];
        right[1] = EnemyAnim[3][1];
        right[2] = EnemyAnim[3][2];
        right[3] = EnemyAnim[3][3];
        RightwalkAnimation = new Animation(0.2f, right);


        TextureRegion[] up = new TextureRegion[4];
        up[0] = EnemyAnim[1][0];
        up[1] = EnemyAnim[1][1];
        up[2] = EnemyAnim[1][2];
        up[3] = EnemyAnim[1][3];
        UpwalkAnimation = new Animation(0.2f, up);

        TextureRegion[] Down = new TextureRegion[4];
        Down[0] = EnemyAnim[0][0];
        Down[1] = EnemyAnim[0][1];
        Down[2] = EnemyAnim[0][2];
        Down[3] = EnemyAnim[0][3];
        DownwalkAnimation = new Animation(0.2f, Down);

        TextureRegion[] Left = new TextureRegion[4];
        Left[0] = EnemyAnim[2][0];
        Left[1] = EnemyAnim[2][1];
        Left[2] = EnemyAnim[2][2];
        Left[3] = EnemyAnim[2][3];
        LeftwalkAnimation = new Animation(0.2f, Left);

    }
    public void render(Batch batch){
        if (!(IsMovingLeft) && !(IsMovingDown) && !(IsMovingRight) && !(IsMovingUp)) {
            IsStaying = true;
            Animation(batch);

        } else {
            IsStaying = false;
            Animation(batch);
        }
    }
    protected void Animation(Batch batch){
        while (IsMovingRight) {
            TextureRegion currentFrame = (TextureRegion) RightwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 16);
            return;
        }
        while (IsMovingUp) {
            TextureRegion currentFrame = (TextureRegion) UpwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 16);
            return;
        }
        while (IsMovingDown) {
            TextureRegion currentFrame = (TextureRegion) DownwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 16);
            return;
        }

        while (IsMovingLeft) {
            TextureRegion currentFrame = (TextureRegion) LeftwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 16);
            return;
        }
        while (IsStaying) {
            batch.draw(EnemyAnim[0][0], pos.x, pos.y, 16, 16);
            return;
        }
    }
    protected void RandomMoving(){


    }
    public void Die(){

    }
    public Circle GetProj(){
        return null;
    }


    public abstract void render(Batch batch, Vector2 PosProj);
    public void InterPolation(float prevX, float prevY, float x, float y, Timestamp start, Timestamp end){
        ObjectInterpolator Inter = new ObjectInterpolator(
                prevX,
                prevY,
                x,
                y,
                start,
                end,
                Gdx.graphics.getDeltaTime()
        );
        Inter.startInterpolation();

        Inter.update();

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
            if (startX - endX != 1 || startX - endX != -1 || startY - endY != 1 || startY - endY != -1) {
                if (startX < endX) {
                    IsMovingRight = true;
                } else {
                    IsMovingRight = false;
                }
                if (startX > endX) {
                    IsMovingLeft = true;
                } else {
                    IsMovingLeft = false;
                }
                if (startY < endY) {
                    IsMovingUp = true;
                } else {
                    IsMovingUp = false;
                }
                if (startY > endY) {
                    IsMovingDown = true;
                } else {
                    IsMovingDown = false;
                }
            }
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

            MoveTo(new Vector2(x-pos.x, y-pos.y));

        }
    }
    public void MoveTo(Vector2 dir){
        pos.add(dir);
    }
}
