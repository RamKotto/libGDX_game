package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Boy;
import com.mygdx.game.Main;

public class LevelOneScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture img;
    private Boy player;

    //lesson 4
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float CAMERA_ZOOM = 0.5f;

    // для ограничения перемещения камеры или персонажа
    private Rectangle mapSize;

    public LevelOneScreen(Main game) {
        batch = new SpriteBatch();
        this.game = game;
        this.player = new Boy();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/level_one.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("объекты").getObjects().get("камера");
        camera.position.x = tmp.getRectangle().x;
        camera.position.y = tmp.getRectangle().y;
        camera.zoom = CAMERA_ZOOM;

        // для ограничения перемещения камеры или персонажа
        mapSize = ((RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница")).getRectangle();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // позиция камеры
        camera.position.x = player.getX();
        camera.position.y = player.getY();

        // управление зумом
        if (Gdx.input.isKeyPressed(Input.Keys.P) && camera.zoom > 0.2) camera.zoom -= 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom < 1.9) camera.zoom += 0.1f;

        // обновление позиции камеры
        camera.update();

        // установка заднего фона
        ScreenUtils.clear(Color.LIGHT_GRAY);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(player.move(), player.getX(), 18, player.getWidth(), player.getHeight());
        batch.end();

        // отрисовка карты и указание ее местоположения
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        player.dispose();
        game.dispose();
        map.dispose();
    }
}
