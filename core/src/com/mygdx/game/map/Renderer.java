package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Player;

public class Renderer extends OrthogonalTiledMapRenderer {
    public Renderer(TiledMap map) {
        super(map);
    }

    public Renderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public Renderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public Renderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public void render(Player player1, Player player2, Enemy[] enemies) {
        beginRender();
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("Игрок за эти")){
                player1.render(getBatch());
                player2.render(getBatch());
                for (int i = 0; i < 1; i++) {
                    enemies[i].render(getBatch(), player1.getPos());
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
