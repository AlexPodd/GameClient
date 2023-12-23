package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.sql.Timestamp;
import java.util.Vector;

public abstract class Entity {

    protected Vector2 pos = new Vector2();

    protected float MoveSpeed;
    protected float AttackSpeed;

    protected float HP;
    protected float Damage;

    protected int width;
    protected int hight;
    protected Rectangle Hitbox;
    public boolean IsAttacking;
    public boolean IsMovingLeft;
    public boolean IsMovingRight;
    public boolean IsMovingUp;
    public boolean IsMovingDown;

    public void setHP(float HP) {
        this.HP = HP;
    }

    public Entity(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed, int width, int hight){
        this.HP = HP;
        this.pos.set(x,y);
        this.Damage = Damage;
        this.AttackSpeed = AttackSpeed;
        this.MoveSpeed = MoveSpeed;
        this.width = width;
        this.hight = hight;
    }
    public Rectangle CreateHitbox() {
        return Hitbox = new Rectangle(pos.x, pos.y, width, hight);
    }


    public class ObjectInterpolator {
        private float startX, startY;
        private float endX, endY;
        private float duration;
        private float currentTime;
        private boolean isInterpolating;
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

                float interpolatedX = Interpolation.smoother.apply(startX, endX, progress);
                float interpolatedY = Interpolation.smoother.apply(startY, endY, progress);


                updateObjectPosition(interpolatedX, interpolatedY);
            }

        }

        private void updateObjectPosition(float x, float y) {


            MoveTo(new Vector2(x-pos.x, y-pos.y));

        }
    }
    public void MoveTo(Vector2 dir) {
        pos.add(dir);
        Hitbox.set(pos.x, pos.y, width, hight);
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

    public void setPos(Vector2 pos) {
        this.pos = pos;
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
}
