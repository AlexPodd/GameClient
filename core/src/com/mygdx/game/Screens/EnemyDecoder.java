package com.mygdx.game.Screens;

import com.badlogic.gdx.math.Vector2;

public class EnemyDecoder {
    private Vector2 PosEnemy;
    private Vector2 PosProj;
    private int enemyID;
    private char EnemyType;
    private char EnemyState;
    public EnemyDecoder(String input){
        String[] Words = input.split("#");
        this.enemyID = input.charAt(0);
        this.EnemyType = input.charAt(1);
        this.EnemyState = input.charAt(2);
        this.PosEnemy = new Vector2(Float.parseFloat(Words[1]),Float.parseFloat(Words[2]));
        this.PosProj = new Vector2(Float.parseFloat(Words[3]), Float.parseFloat(Words[4]));
    }
}