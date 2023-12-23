package com.mygdx.game.ServernayaChast;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Skeleton;

public class EnemyDecoder {
    private Vector2 PosEnemy;
    private Vector2 PosProj;
    private int enemyHP;
    private String EnemyType;
    private String EnemyState;
    public EnemyDecoder(){
    }
    public Enemy DecodeEnemy(String input){
        String[] Words = input.split("#");
        this.enemyHP = Integer.parseInt(String.valueOf(input.charAt(2)));
        this.EnemyType = String.valueOf(input.charAt(0));
        this.EnemyState = (String.valueOf(input.charAt(1)));
        this.PosEnemy = new Vector2(Float.parseFloat(Words[1]),Float.parseFloat(Words[2]));
        this.PosProj = new Vector2(Float.parseFloat(Words[3]), Float.parseFloat(Words[4]));
        switch (EnemyType){
            case "S": return new Skeleton(PosEnemy.x, PosEnemy.y, 3, 1, 2000, 0, PosProj);
        }
        return null;
    }
    public Enemy DecodeToRender(String input){
        String[] Words = input.split("#");
        EnemyState = Words[1];
        PosEnemy = new Vector2(Float.parseFloat(Words[2]),Float.parseFloat(Words[3]));
        enemyHP = Integer.parseInt(Words[4]);
        PosProj = new Vector2(Float.parseFloat(Words[5]),Float.parseFloat(Words[6]));
         switch (Words[0]){
             case "S":

                 return new Skeleton(PosEnemy.x, PosEnemy.y, enemyHP, 1, 2000, 0, PosProj);
         }
        return null;
    }
}