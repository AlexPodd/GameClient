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
    protected boolean IsStaying;
    protected TextureRegion[][] EnemyAnim;
    protected float stateTime;

    protected Animation RightwalkAnimation, UpwalkAnimation, DownwalkAnimation, LeftwalkAnimation;
    public Enemy(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed, 16, 16);

    }
    public void SetAnim(){
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
}
