package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Boy;
import com.mygdx.game.Main;
import com.mygdx.game.PhysX;

public class LevelOneScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture img;
    private Boy player;

    //lesson 4
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float CAMERA_ZOOM = 0.6f;

    // lesson 5
    private final int[] bg;
    private final int[] l1;
    //    private Array<RectangleMapObject> objects;
    private ShapeRenderer shapeRenderer;
    private PhysX physX;
    private Body body;

    public LevelOneScreen(Main game) {
        batch = new SpriteBatch();
        this.game = game;
        this.player = new Boy();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/level_one.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera.zoom = CAMERA_ZOOM;

        shapeRenderer = new ShapeRenderer();

        bg = new int[1];
        l1 = new int[1];
        bg[0] = map.getLayers().getIndex("фон");
        l1[0] = map.getLayers().getIndex("слой 1");

        physX = new PhysX();

        RectangleMapObject cameraObject = (RectangleMapObject) map.getLayers().get("сеттинг").getObjects().get("hero");
        body = physX.addObject(cameraObject, "boy");

        Array<RectangleMapObject> objects = map.getLayers().get("объекты").getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i), "стена");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // позиция камеры
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;

        // управление зумом
        if (Gdx.input.isKeyPressed(Input.Keys.P) && camera.zoom > 0.2) camera.zoom -= 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom < 1.9) camera.zoom += 0.1f;

        // обновление позиции камеры
        camera.update();

        // установка заднего фона
//        ScreenUtils.clear(Color.LIGHT_GRAY);
        ScreenUtils.clear(Color.BLACK);

        // отрисовка карты (слой "фон") и указание ее местоположения
        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        // отрисовка игрока, указание его стартовой позиции,ширины и высоты его отображения
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(player.move(), player.getX(), 18, player.getWidth(), player.getHeight());
        batch.end();

        // отрисовка слоя "слой 1"
        mapRenderer.render(l1);

        // обводим все прямоугольники из слоя "объекты", для отладки.
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        for (int i = 0; i < objects.size; i++) {
//            Rectangle rectObject = objects.get(i).getRectangle();
//            shapeRenderer.rect(rectObject.x, rectObject.y, rectObject.width, rectObject.height);
//        }
//        shapeRenderer.end();

        physX.step();
        physX.debugDraw(camera);
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
        shapeRenderer.dispose();
        physX.dispose();
    }
}
