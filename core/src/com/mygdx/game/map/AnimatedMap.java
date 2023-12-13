package com.mygdx.game.map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class AnimatedMap {

    private TiledMap map;

    public AnimatedTiledMapTile prepareTile(String name, float speed, String property, String value) {

        Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(3);

        Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("Overworld").iterator();
        while (tiles.hasNext()) {
            TiledMapTile tile = tiles.next();
            if (tile.getProperties().containsKey("animation")
                    && tile.getProperties().get("animation", String.class).equals(name)) {
                frameTiles.add((StaticTiledMapTile) tile);
            }
        }

        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(speed, frameTiles);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Непроходимые");

        for (int x = 0; x < layer.getWidth(); x++)
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    if (cell.getTile().getProperties().containsKey("animation")
                            && cell.getTile().getProperties().get("animation", String.class).equals(name)) {
                        cell.setTile(animatedTile);
                    }
                }
            }
        animatedTile.getProperties().put(property, value);

        return animatedTile;
    }

    public AnimatedMap(TiledMap map) {

        this.map = map;
        float waterSpeed = 0.1f;

        prepareTile("WaterFrame1", waterSpeed, "water", "");
        prepareTile("WaterFrame2", waterSpeed, "water", "");
        prepareTile("WaterFrame3", waterSpeed, "water", "");
    }

}
