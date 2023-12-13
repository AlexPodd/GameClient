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

    public boolean IsMovingLeft;
    public boolean IsMovingRight;
    public boolean IsMovingUp;
    public boolean IsMovingDown;

    public boolean OpenDoor;
    public boolean IsStaying;

    public final int width = 16;
    public final int hight = 32;

    private Rectangle Hitbox;
    private Vector2 pos = new Vector2();

    private float stateTime, AttakingTime;
    private int number;
    public boolean IsAttacking;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    public void SetPos(Vector2 UpdatePos){
        pos.set(UpdatePos);
        Hitbox.set(UpdatePos.x,UpdatePos.y,width,hight);
    }
    public float GetHP(){
        return HP;
    }
    private Animation RightwalkAnimation, UpwalkAnimation, DownwalkAnimation, LeftwalkAnimation, DownAttackAnimation,LeftAttackAnimation, RightAttackAnimation, UpAttackAnimation ;

    public Player(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed, Texture text) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);
        pos.set(x, y);
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




    public float getMoveSpeed() {
        return MoveSpeed;
    }

    public Rectangle CreateHitbox() {
        return Hitbox = new Rectangle(pos.x, pos.y, width, hight);
    }

    public void MoveTo(Vector2 dir) {
        pos.add(dir);
        Hitbox.set(pos.x, pos.y, width, hight);
    }

    public Rectangle getObjectHitbox() {
        return Hitbox;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public void Animation(Batch batch) {
       while (IsAttacking&&IsMovingDown){
           TextureRegion currentFrame = (TextureRegion) DownAttackAnimation.getKeyFrame(AttakingTime, false);
           batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (IsAttacking&&IsMovingUp){
            TextureRegion currentFrame = (TextureRegion) UpAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (IsAttacking&&IsMovingLeft){
            TextureRegion currentFrame = (TextureRegion) LeftAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
            return;
        }
        while (IsAttacking&&IsMovingRight){
            TextureRegion currentFrame = (TextureRegion) RightAttackAnimation.getKeyFrame(AttakingTime, false);
            batch.draw(currentFrame, pos.x, pos.y, 32, 36);
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






    public int GetXPosTile1(float Correcter) {
        int counter = 0;
        float position;
        position = pos.x + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetXPosTile2(float Correcter) {
        int counter = 0;
        float position;
        position = pos.x + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetXPosTile3(float Correcter) {
        int counter = 0;
        float position;
        position = pos.x + width + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetXPosTile4(float Correcter) {
        int counter = 0;
        float position;
        position = pos.x + width + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetYPosTile1(float Correcter) {
        int counter = 0;
        float position;
        position = pos.y + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetYPosTile2(float Correcter) {
        int counter = 0;
        float position;
        position = pos.y + hight + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetYPosTile3(float Correcter) {
        int counter = 0;
        float position;
        position = pos.y + hight + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public int GetYPosTile4(float Correcter) {
        int counter = 0;
        float position;
        position = pos.y + Correcter;
        while (position - 32 > 0) {
            counter++;
            position -= 32;
        }
        return counter;
    }

    public boolean CanMoveHere(TiledMap map) {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get("Непроходимые");
        TiledMapTileLayer tileLayer1 = (TiledMapTileLayer) map.getLayers().get("верхний слой");
        if(OpenDoor){
           if( Door(map)){
               return true;
           }
        }
        if (IsMovingRight) {
            if(pos.x+MoveSpeed > 3168){
                return false;
            }
            if (((tileLayer.getCell(GetXPosTile1(MoveSpeed), GetYPosTile1(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile2(MoveSpeed), GetYPosTile2(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile3(MoveSpeed), GetYPosTile3(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile4(MoveSpeed), GetYPosTile4(0)) != null)) &&
                    ((tileLayer1.getCell(GetXPosTile3(MoveSpeed), GetYPosTile3(0)) == null) &&
                    (tileLayer1.getCell(GetXPosTile4(MoveSpeed), GetYPosTile4(0)) == null) )
            ) {
                return false;
            }
        }
        if (IsMovingLeft) {
            if(pos.x-MoveSpeed <0){
                return false;
            }
            if (((tileLayer.getCell(GetXPosTile1(-MoveSpeed), GetYPosTile1(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile2(-MoveSpeed), GetYPosTile2(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile3(-MoveSpeed), GetYPosTile3(0)) != null) ||
                    (tileLayer.getCell(GetXPosTile4(-MoveSpeed), GetYPosTile4(0)) != null)) &&
                    ((tileLayer1.getCell(GetXPosTile1(-MoveSpeed), GetYPosTile1(0)) == null) &&
                            (tileLayer1.getCell(GetXPosTile2(-MoveSpeed), GetYPosTile2(0)) == null)
                             )
            ){
                return false;
            }
        }
        if (IsMovingUp) {
            if(pos.y+MoveSpeed > 3168){
                return false;
            }
            if (((tileLayer.getCell(GetXPosTile1(0), GetYPosTile1(MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile2(0), GetYPosTile2(MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile3(0), GetYPosTile3(MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile4(0), GetYPosTile4(MoveSpeed)) != null)) &&
                    (
                            (tileLayer1.getCell(GetXPosTile2(0), GetYPosTile2(MoveSpeed)) == null) &&
                            (tileLayer1.getCell(GetXPosTile3(0), GetYPosTile3(MoveSpeed)) == null)
                           )
            ){
                return false;
            }
        }

        if (IsMovingDown) {

            if(pos.y-MoveSpeed <0){
                return false;
            }
            if  (((tileLayer.getCell(GetXPosTile1(0), GetYPosTile1(-MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile2(0), GetYPosTile2(-MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile3(0), GetYPosTile3(-MoveSpeed)) != null) ||
                    (tileLayer.getCell(GetXPosTile4(0), GetYPosTile4(-MoveSpeed)) != null)) &&
                    ((tileLayer1.getCell(GetXPosTile1(0), GetYPosTile1(-MoveSpeed)) == null) &&
                            (tileLayer1.getCell(GetXPosTile4(0), GetYPosTile4(-MoveSpeed)) == null) )
            ){
                return false;
            }
        }




        return true;
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
        public ObjectInterpolator(float startX, float startY, float endX, float endY, Timestamp StartTs, Timestamp EndTs,float delta) {
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

            MoveTo(new Vector2(x-getX(), y-getY()));

        }
    }
}
