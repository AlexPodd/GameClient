package com.mygdx.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Player;
import com.mygdx.game.map.LvlManager;

import java.util.Arrays;

public class input extends InputAdapter {
    private boolean LeftPressed, RightPressed, UpPressed, DownPressed, AttackPressed;

    private Vector2 direction = new Vector2();
    private Player player;
    private LvlManager lvl;


    public input(Player player){
        this.player = player;
        lvl= new LvlManager();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A) {
            player.IsMovingLeft = true;
            return LeftPressed = true;
        }
        if(keycode == Input.Keys.D){
           player.IsMovingRight = true;
            return RightPressed = true;
        }
        if(keycode == Input.Keys.S){
            player.IsMovingDown = true;
            return DownPressed = true;
        }
        if(keycode == Input.Keys.W){
            player.IsMovingUp = true;
            return UpPressed = true;
        }
        if(keycode == Input.Keys.SPACE){
            player.IsAttacking = true;
            return AttackPressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A){
            player.IsMovingLeft = false;
            return LeftPressed = false;
        }
        if(keycode == Input.Keys.D) {
            player.IsMovingRight = false;
            return RightPressed = false;
        }
        if(keycode == Input.Keys.S){
            player.IsMovingDown = false;
            return DownPressed = false;
        }
        if(keycode == Input.Keys.W){
            player.IsMovingUp = false;
            return UpPressed = false;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    return false;
    }

    public Vector2 getDir(){
        direction.set(0,0);
        if (player.IsAttacking){
        return direction;
        }
            if(LeftPressed){
                if(!player.CanMoveHere(lvl.map)){
                    return direction;
                }
                direction.add(-player.getMoveSpeed(),0);
            }
            if(RightPressed){
               if(!player.CanMoveHere(lvl.map)){
                   return direction;
               }
                direction.add(player.getMoveSpeed(),0);
            }
        if(UpPressed){
            if(!player.CanMoveHere(lvl.map)){
                return direction;
            }
            direction.add(0,player.getMoveSpeed());
        }

        if(DownPressed){
            if(!player.CanMoveHere(lvl.map)){
                return direction;
            }
            direction.add(0,-player.getMoveSpeed());
        }
        return direction;
    }

}
