package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Player;

import java.util.LinkedList;

public class Renderer extends OrthogonalTiledMapRenderer {
    private Texture boloto;
    public Renderer(TiledMap map) {
        super(map);
    }

    public Renderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public Renderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        boloto = new Texture("assets/Boloto.png");
    }

    public Renderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public void render(Player player1, Player player2, LinkedList<Enemy> enemies) {
        beginRender();
        batch.draw(boloto,-400,-400);
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("Игрок за эти")){
                player1.render(getBatch());
                player2.renderPlayer2(getBatch());
                for (Enemy enemy : enemies) {
                    enemy.SetAnim();
                    enemy.render(getBatch());
                }
            }
            renderMapLayer(layer);
        }
        endRender();
    }

    @Override
    protected void renderMapLayer(MapLayer layer) {
        super.renderMapLayer(layer);
    }

    @Override
    protected void beginRender() {
        super.beginRender();
    }

    @Override
    protected void endRender() {
        super.endRender();
    }
}
