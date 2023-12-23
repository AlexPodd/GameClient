package com.mygdx.game.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

import java.awt.geom.Rectangle2D;
import java.sql.Timestamp;


public class Player extends Entity {

    private TextureRegion[][] textPl;
    private TextureRegion[][] textPlAttack;

    public Vector2 getPos() {
        return pos;
    }

    public boolean OpenDoor;
    public boolean IsStaying;
    private String Attack;

    private float stateTime, AttakingTime;
    private int number;


    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    public float GetHP(){
        return HP;
    }
    private Animation RightwalkAnimation, UpwalkAnimation, DownwalkAnimation, LeftwalkAnimation, DownAttackAnimation,LeftAttackAnimation, RightAttackAnimation, UpAttackAnimation ;

    public Player(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed, Texture text) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed, 16, 32);
        IsAttacking = false;
        Hitbox = CreateHitbox();
        textPl = new TextureRegion[text.getWidth()][text.getHeight()];
        textPlAttack = new TextureRegion[text.getWidth()][text.getHeight()];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 15; j++) {
                textPl[i][j] = new TextureRegion(text, i * 16, j * 32, 16, 32);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                textPlAttack[i][j] = new TextureRegion(text,i*32 ,4*32+j*32 , 32, 32);
            }
        }
        stateTime = 0F;
        AttakingTime = 0F;
        OpenDoor = false;
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


        TextureRegion[] DownAttack = new TextureRegion[4];
        DownAttack[0] = textPlAttack[0][0];
        DownAttack[1] = textPlAttack[1][0];
        DownAttack[2] = textPlAttack[2][0];
        DownAttack[3] = textPlAttack[3][0];
        DownAttackAnimation = new Animation(0.1f,DownAttack);

        TextureRegion[] UpAttack = new TextureRegion[4];
        UpAttack[0] = textPlAttack[0][1];
        UpAttack[1] = textPlAttack[1][1];
        UpAttack[2] = textPlAttack[2][1];
        UpAttack[3] = textPlAttack[3][1];
        UpAttackAnimation = new Animation(0.1f,UpAttack);

        TextureRegion[] RightAttack = new TextureRegion[4];
        RightAttack[0] = textPlAttack[0][2];
        RightAttack[1] = textPlAttack[1][2];
        RightAttack[2] = textPlAttack[2][2];
        RightAttack[3] = textPlAttack[3][2];
        RightAttackAnimation = new Animation(0.1f,RightAttack);

        TextureRegion[] LeftAttack = new TextureRegion[4];
        LeftAttack[0] = textPlAttack[0][3];
        LeftAttack[1] = textPlAttack[1][3];
        LeftAttack[2] = textPlAttack[2][3];
        LeftAttack[3] = textPlAttack[3][3];
       LeftAttackAnimation = new Animation(0.1f,LeftAttack);
    Attack ="NN";
    }
    public String GetAttack(){
        String temp = Attack;
        Attack = "NN";
        return temp;
    }
    public void setAttack(String attack){
        this.Attack = attack;
        this.IsAttacking = true;
    }
    public void SetHp(float HP){
        this.HP = HP;
    }
    public void render(Batch batch) {
        if(!IsAttacking){
            AttakingTime = 0F;
        }
        if(IsAttacking){
            AttakingTime+=Gdx.graphics.getDeltaTime();
        }
        if(AttakingTime > AttackSpeed){
            IsAttacking = false;
        }
        stateTime += Gdx.graphics.getDeltaTime();
        if (!(IsMovingLeft) && !(IsMovingDown) && !(IsMovingRight) && !(IsMovingUp)) {
            IsStaying = true;
            Animation(batch);

        } else {
            IsStaying = false;
            Animation(batch);
        }
    }
    public void AnimationAttack(Batch batch) {
        while (Attack.equals("DA")){
            TextureRegion currentFrame = (TextureRegion) DownAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (Attack.equals("UA")){
            TextureRegion currentFrame = (TextureRegion) UpAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (Attack.equals("LA")){
            TextureRegion currentFrame = (TextureRegion) LeftAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (Attack.equals("RA")){
            TextureRegion currentFrame = (TextureRegion) RightAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
    }
    public void renderPlayer2(Batch batch) {
        if(!IsAttacking){
            AttakingTime = 0F;
        }
        if(AttakingTime > AttackSpeed){
            IsAttacking = false;
        }
        if(IsAttacking){
            AttakingTime+=Gdx.graphics.getDeltaTime();
            AnimationAttack(batch);
            return;
        }
        stateTime += Gdx.graphics.getDeltaTime();
        if (!(IsMovingLeft) && !(IsMovingDown) && !(IsMovingRight) && !(IsMovingUp)) {
            IsStaying = true;
            Animation(batch);

        } else {
            IsStaying = false;
            Animation(batch);
        }
    }




    public float getMoveSpeed() {
        return MoveSpeed;
    }


    public void Animation(Batch batch) {
       while (IsAttacking&&IsMovingDown){
           TextureRegion currentFrame = (TextureRegion) DownAttackAnimation.getKeyFrame(AttakingTime, false);
           batch.draw(currentFrame, pos.x, pos.y, 32, 36);
           Attack = "DA";
            return;
        }
        while (IsAttacking&&IsMovingUp){
            TextureRegion currentFrame = (TextureRegion) UpAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            Attack = "UA";
            return;
        }
        while (IsAttacking&&IsMovingLeft){
            TextureRegion currentFrame = (TextureRegion) LeftAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            Attack = "LA";
            return;
        }
        while (IsAttacking&&IsMovingRight){
            TextureRegion currentFrame = (TextureRegion) RightAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            Attack = "RA";
            return;
        }
        while (IsMovingRight) {
            TextureRegion currentFrame = (TextureRegion) RightwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsMovingUp) {
            TextureRegion currentFrame = (TextureRegion) UpwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsMovingDown) {
            TextureRegion currentFrame = (TextureRegion) DownwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }

        while (IsMovingLeft) {
            TextureRegion currentFrame = (TextureRegion) LeftwalkAnimation.getKeyFrame(stateTime, true); // #16
            batch.draw(currentFrame, pos.x, pos.y, 16, 36);
            return;
        }
        while (IsStaying) {
            batch.draw(textPl[0][0], pos.x, pos.y, 16, 36);
            return;
        }
    }
    @Override
    public boolean CanMoveHere(TiledMap map) {
        if(OpenDoor){
            if( Door(map)){
                return true;
            }
        }
        return super.CanMoveHere(map);
    }


    private boolean Door(TiledMap map){
        TiledMapTileLayer tileLayer2 = (TiledMapTileLayer) map.getLayers().get("ОткрытаяДверь");
        if (IsMovingUp){
            if((tileLayer2.getCell(GetXPosTile1(0), GetYPosTile1(MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile2(0), GetYPosTile2(MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile3(0), GetYPosTile3(MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile4(0), GetYPosTile4(MoveSpeed)) != null)
            )
            {
             return true;
            }
        }
        if (IsMovingDown){
            if((tileLayer2.getCell(GetXPosTile1(0), GetYPosTile1(-MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile2(0), GetYPosTile2(-MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile3(0), GetYPosTile3(-MoveSpeed)) != null)||
                    (tileLayer2.getCell(GetXPosTile4(0), GetYPosTile4(-MoveSpeed)) != null)
            )
            {
                return true;
            }
        }
        if (IsMovingRight){
            if((tileLayer2.getCell(GetXPosTile1(MoveSpeed), GetYPosTile1(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile2(MoveSpeed), GetYPosTile2(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile3(MoveSpeed), GetYPosTile3(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile4(MoveSpeed), GetYPosTile4(0)) != null)
            )
            {
                return true;
            }
        }
        if (IsMovingLeft){
            if((tileLayer2.getCell(GetXPosTile1(-MoveSpeed), GetYPosTile1(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile2(-MoveSpeed), GetYPosTile2(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile3(-MoveSpeed), GetYPosTile3(0)) != null)||
                    (tileLayer2.getCell(GetXPosTile4(-MoveSpeed), GetYPosTile4(0)) != null)
            )
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void InterPolation(float prevX, float prevY, float x, float y, Timestamp start, Timestamp end) {
        super.InterPolation(prevX, prevY, x, y, start, end);
    }
}
