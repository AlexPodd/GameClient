package com.mygdx.game.ServernayaChast;


import com.mygdx.game.entity.Enemy;

import java.util.LinkedList;

public class PrepareForGame {
    private static PrepareForGame instance;
    private String enemies;

    private PrepareForGame() {
    }

    public static PrepareForGame getInstance() {
        if (instance == null) {
            instance = new PrepareForGame();
        }
        return instance;
    }

    public void setEnemies(String E) {
        enemies = E;
    }

    public String getEnemies() {
        return enemies;
    }
}
