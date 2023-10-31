package com.mygdx.game.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Rectangle2D;


public class Player extends Entity{

    private TextureRegion[][] textPl;





    public boolean IsMovingLeft;
    public boolean IsMovingRight;
    public boolean IsMovingUp;
    public boolean IsMovingDown;

    public boolean IsStaying;

    public final int width = 16;
    public final int hight = 32;

    private Rectangle2D Hitbox;
    private Vector2 pos = new Vector2();

    private float stateTime;



    private Animation RightwalkAnimation, UpwalkAnimation, DownwalkAnimation, LeftwalkAnimation;
    public Player(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed, Texture text) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);
        pos.set(x,y);
        Hitbox = CreateHitbox();
        textPl= new TextureRegion[text.getWidth()][text.getHeight()];
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j <15 ; j++) {
                textPl[i][j] =new TextureRegion(text,i*16, j*32,16,32);
            }
        }
        stateTime = 0F;

        TextureRegion[] right = new TextureRegion[4];
        right[0] = textPl[0][1];
        right[1] = textPl[1][1];
        right[2] = textPl[2][1];
        right[3] = textPl[3][1];
        RightwalkAnimation = new Animation(0.2f, right);


        TextureRegion[] up = new TextureRegion[4];
        up[0] = textPl[0][2];
        up[1] = textPl[1][2];
        up[2] = textPl[2][2];
        up[3] = textPl[3][2];
        UpwalkAnimation = new Animation(0.2f, up);

        TextureRegion[] Down = new TextureRegion[4];
        Down[0] = textPl[0][0];
        Down[1] = textPl[1][0];
        Down[2] = textPl[2][0];
        Down[3] = textPl[3][0];
        DownwalkAnimation = new Animation(0.2f, Down);

        TextureRegion[] Left = new TextureRegion[4];
        Left[0] = textPl[0][3];
        Left[1] = textPl[1][3];
        Left[2] = textPl[2][3];
        Left[3] = textPl[3][3];
        LeftwalkAnimation = new Animation(0.2f, Left);




    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if (!(IsMovingLeft) && !(IsMovingDown) && !(IsMovingRight) && !(IsMovingUp)) {
            IsStaying = true;
            Animation(batch);

            }
        else {
            IsStaying = false;
            Animation(batch);
        }
    }

    public float getMoveSpeed() {
        return MoveSpeed;
    }

    public Rectangle2D CreateHitbox(){
        return Hitbox= new Rectangle2D.Float(pos.x,pos.y,width,hight);
    }
    public void MoveTo(Vector2 dir){
        pos.add(dir);
        Hitbox.setRect(pos.x,pos.y,width,hight);
    }

    public Rectangle2D getObjectHitbox() {
        return Hitbox;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }
    public void Animation(Batch batch){
        while (IsMovingRight) {
            TextureRegion currentFrame = (TextureRegion) RightwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsMovingUp){
            TextureRegion currentFrame = (TextureRegion) UpwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsMovingDown){
            TextureRegion currentFrame = (TextureRegion) DownwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }

        while (IsMovingLeft){
            TextureRegion currentFrame = (TextureRegion) LeftwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsStaying){
            batch.draw(textPl[0][0], pos.x, pos.y, 16, 36);
            return;
        }
    }
    public int GetXPosTile1(float Correcter){
        int counter = 0;
        float position;
        position = pos.x+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public int GetXPosTile2(float Correcter){
        int counter = 0;
        float position;
        position = pos.x+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public int GetXPosTile3(float Correcter){
        int counter = 0;
        float position;
        position = pos.x+width+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public int GetXPosTile4(float Correcter){
        int counter = 0;
        float position;
        position = pos.x+width+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }

    public int GetYPosTile1(float Correcter){
        int counter = 0;
        float position;
        position = pos.y+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public int GetYPosTile2(float Correcter){
        int counter = 0;
        float position;
        position = pos.y+hight+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public int GetYPosTile3(float Correcter){
        int counter = 0;
        float position;
        position = pos.y+hight+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
        }
    public int GetYPosTile4(float Correcter){
        int counter = 0;
        float position;
        position = pos.y+Correcter;
        while (position-32>0){
            counter++;
            position-=32;
        }
        return counter;
    }
    public boolean CanMoveHere(int[][]map){
        if(IsMovingRight) {
            if(
                    (map[GetXPosTile3(MoveSpeed)][GetYPosTile3(0)] == 10)||
                    (map[GetXPosTile1(MoveSpeed)][GetYPosTile1(0)] == 10)||
                    (map[GetXPosTile2(MoveSpeed)][GetYPosTile2(0)] == 10)||
                    (map[GetXPosTile4(MoveSpeed)][GetYPosTile4(0)] == 10)

            ){
                return false;
            }
        }
        if(IsMovingLeft) {
            if(
                    (map[GetXPosTile3(-MoveSpeed)][GetYPosTile3(0)] == 10)||
                            (map[GetXPosTile1(-MoveSpeed)][GetYPosTile1(0)] == 10)||
                            (map[GetXPosTile2(-MoveSpeed)][GetYPosTile2(0)] == 10)||
                            (map[GetXPosTile4(-MoveSpeed)][GetYPosTile4(0)] == 10)||
                            (pos.x-MoveSpeed<0)
            ){
                return false;
            }
        }
        if(IsMovingDown) {
            if(
                    (map[GetXPosTile3(0)][GetYPosTile3(-MoveSpeed)] == 10)||
                            (map[GetXPosTile1(0)][GetYPosTile1(-MoveSpeed)] == 10)||
                            (map[GetXPosTile2(0)][GetYPosTile2(-MoveSpeed)] == 10)||
                            (map[GetXPosTile4(0)][GetYPosTile4(-MoveSpeed)] == 10)||
                            (pos.y-MoveSpeed<0)
            ){
                return false;
            }
        }
        if(IsMovingUp) {
            System.out.println(pos.y+MoveSpeed);
            if(

                    (map[GetXPosTile3(0)][GetYPosTile3(MoveSpeed)] == 10)||
                            (map[GetXPosTile1(0)][GetYPosTile1(MoveSpeed)] == 10)||
                            (map[GetXPosTile2(0)][GetYPosTile2(MoveSpeed)] == 10)||
                            (map[GetXPosTile4(0)][GetYPosTile4(MoveSpeed)] == 10)
            ){
                return false;
            }
        }
        return true;
    }
    public void PosInter(Vector2 pos1, Vector2 pos2,float previousTime, float RightNowTime, float MaxDifference){
        float TimeDifference = Math.abs(RightNowTime - previousTime);
        float coefficient = 1 - (TimeDifference - MaxDifference);
        if(coefficient < 0){
            coefficient = 0;
        }
        if(coefficient > 1 ){
            coefficient = 1;
        }
        if (coefficient < 1) {
            float x = Interpolation.linear.apply(pos1.x, pos2.x, coefficient);
            float y = Interpolation.linear.apply(pos1.y, pos2.y, coefficient); //0.5 означает половину движения от движения, то есть коэффициент должен изменяться с 0 до 1 за время обновления (от GameState1 о GameState2)
            pos.set(x, y);
            System.out.println(pos);
        } else {
            pos.set(pos2);
        }
    }
}
