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
    private RectangleMapObject heroObjectFromMap;
//    private Rectangle realPlayerPosition;

    public LevelOneScreen(Main game) {
        this.batch = new SpriteBatch();
        this.game = game;

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.map = new TmxMapLoader().load("map/level_one.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera.zoom = CAMERA_ZOOM;

        shapeRenderer = new ShapeRenderer();

        this.bg = new int[1];
        this.l1 = new int[1];
        bg[0] = map.getLayers().getIndex("фон");
        l1[0] = map.getLayers().getIndex("слой 1");

        this.physX = new PhysX();
        this.heroObjectFromMap = (RectangleMapObject) map.getLayers().get("сеттинг").getObjects().get("hero");
        this.body = physX.addObject(heroObjectFromMap, "boy");
        Array<RectangleMapObject> objects = map.getLayers().get("объекты").getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i), "стена");
        }

        this.player = new Boy(heroObjectFromMap, body);

//        realPlayerPosition = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // позиция камеры
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        System.out.println(body.getLinearVelocity());

        // управление зумом
        if (Gdx.input.isKeyPressed(Input.Keys.P) && camera.zoom > 0.2) camera.zoom -= 0.1f;
        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom < 1.9) camera.zoom += 0.1f;

        // обновление позиции камеры
        camera.update();

        // установка заднего фона
        ScreenUtils.clear(Color.BLACK);

        // отрисовка карты (слой "фон") и указание ее местоположения
        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(player.move(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        batch.end();

        // отрисовка слоя "слой 1"
        mapRenderer.render(l1);

        // обводим все прямоугольники из слоя "объекты", для отладки.
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        Rectangle rectObject = heroObjectFromMap.getRectangle();
//        shapeRenderer.rect(rectObject.x, rectObject.y, rectObject.width, rectObject.height);
//        shapeRenderer.rect(realPlayerPosition.x, realPlayerPosition.y, realPlayerPosition.width, realPlayerPosition.height);
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
