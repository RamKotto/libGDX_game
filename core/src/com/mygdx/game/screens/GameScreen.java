package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Anim;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture img;
    private Anim animation;
    private boolean lookRight = true;
    final float STEP = 150;
    private float xCoordinateForAnimation;
    private Rectangle endGameRec;
    private ShapeRenderer shapeRenderer;
    float windowWidth = Gdx.graphics.getWidth();
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRender;
    private Rectangle mapSize;
    private final int[] bg;
    private final int[] l1;

    public GameScreen(Main game) {
        animation = new Anim("atlas/run_atlas.atlas", 1 / 10f, Animation.PlayMode.LOOP);
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("wc3logos/allianceSmall.png");
        endGameRec = new Rectangle(
                (float) (Gdx.graphics.getWidth() / 2) - (float) (img.getWidth() / 2),
                Gdx.graphics.getHeight() - img.getHeight() * 2,
                img.getWidth(), img.getHeight()
        );
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/карта1.tmx");
        mapRender = new OrthogonalTiledMapRenderer(map);

        // map.getLayers().get("объекты").getObjects().getByType(RectangleMapObject.class);  // выбор по типу
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("сеттинг").getObjects().get("камера");   // выбор по имени
        camera.position.x = tmp.getRectangle().x;
        camera.position.y = tmp.getRectangle().y;
        camera.zoom = 0.5f;

//        mapSize = ((RectangleMapObject) map.getLayers().get("объекты").getObjects().get("граница")).getRectangle();



        bg = new int[1];
        bg[0] = map.getLayers().getIndex("фон");
        l1 = new int[4];
        l1[0] = map.getLayers().getIndex("слой 2");
        l1[1] = map.getLayers().getIndex("слой 3");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(Color.BLACK);
        animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) lookRight = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) lookRight = true;
        if (xCoordinateForAnimation >= mapSize.width - animation.getFrame().getRegionWidth()) lookRight = false;
        if (xCoordinateForAnimation <= 0) lookRight = true;

        if (animation.getFrame().isFlipX() && lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!animation.getFrame().isFlipX() && !lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!lookRight) {
            xCoordinateForAnimation -= this.STEP * Gdx.graphics.getDeltaTime();
            camera.position.x -= this.STEP * Gdx.graphics.getDeltaTime();
        } else {
            xCoordinateForAnimation += this.STEP * Gdx.graphics.getDeltaTime();
            camera.position.x += this.STEP * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P) && camera.zoom > 0) camera.zoom -= 0.01f;
        if (Gdx.input.isKeyPressed(Input.Keys.O)) camera.zoom += 0.01f;

//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            float x = Gdx.input.getX();
//            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
//            if (endGameRec.contains(x, y)) {
//                dispose();
//                game.setScreen(new MenuScreen(game));
//            }
//        }

        mapRender.setView(camera);
        mapRender.render(bg);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, endGameRec.x, endGameRec.y, endGameRec.width, endGameRec.height);
        batch.draw(animation.getFrame(), xCoordinateForAnimation, 0);
        batch.end();

        mapRender.render(l1);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(endGameRec.x, endGameRec.y, endGameRec.width, endGameRec.height);
        shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
        shapeRenderer.end();
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
        this.batch.dispose();
        this.img.dispose();
        this.animation.dispose();
        this.map.dispose();
    }
}
