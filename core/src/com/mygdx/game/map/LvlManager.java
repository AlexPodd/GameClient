package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameState.HUD;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Player;

public class LvlManager {
    public TiledMap map;
    public Renderer renderer;

    private HUD hud;
public LvlManager(){
    TmxMapLoader mapLoader = new TmxMapLoader();
    map = mapLoader.load("Lvl1.tmx");

    renderer = new Renderer(map, 2);
    map.getLayers().get("ОткрытаяДверь").setVisible(false);
     hud = new HUD((Viewport) new ScreenViewport(), renderer.getBatch());

}
public void RenderMap(Camera camera, Player player1, Player player2, Enemy[] enemies){
    renderer.setView((OrthographicCamera) camera);
    renderer.render(player1, player2,enemies);
    hud.UpdateHud(player1.GetHP());
    hud.draw();
    boolean opendoor = false;
    MapObjects objects = map.getLayers().get("Кнопки").getObjects();
    int objectCount = objects.getCount();
    for (int i = 0; i < objectCount; i++) {
        MapObject object = objects.get(i);
        Rectangle objectBounds = ((RectangleMapObject) object).getRectangle();
        if (objectBounds.contains(player1.getX() / 2, player1.getY() / 2) || objectBounds.contains(player2.getX() / 2, player2.getY() / 2)
        ) {
            OpenDoor();
            opendoor = true;
            player1.OpenDoor = true;
            }
        }
    /*if (!opendoor){
        CloseDoor();
    }*/
    }
public void OpenDoor(){
   map.getLayers().get("ОткрытаяДверь").setVisible(true);
}

    public void CloseDoor(){
        map.getLayers().get("ОткрытаяДверь").setVisible(false);
    }
}
