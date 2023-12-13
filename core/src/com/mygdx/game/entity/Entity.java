package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

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

}
