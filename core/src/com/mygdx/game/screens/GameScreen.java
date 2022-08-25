package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private float xCoordinateForAnimation;
    private Rectangle endGameRec;
    private ShapeRenderer shapeRenderer;
    float windowWidth = Gdx.graphics.getWidth();

    public GameScreen(Main game) {
        animation = new Anim("atlas/run_atlas.atlas", 1 / 10f, Animation.PlayMode.LOOP);
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("wc3logos/alianceSmall.png");
        endGameRec = new Rectangle(
                (float) (Gdx.graphics.getWidth() / 2) - (float) (img.getWidth() / 2),
                Gdx.graphics.getHeight() - img.getHeight() * 2,
                img.getWidth(), img.getHeight()
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) lookRight = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) lookRight = true;
        if (xCoordinateForAnimation >= windowWidth - animation.getFrame().getRegionWidth()) lookRight = false;
        if (xCoordinateForAnimation <= 0) lookRight = true;

        if (animation.getFrame().isFlipX() && lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!animation.getFrame().isFlipX() && !lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!lookRight) {
            xCoordinateForAnimation -= 5;
        } else {
            xCoordinateForAnimation += 5;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (endGameRec.contains(x, y)) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        }

        batch.begin();
        batch.draw(animation.getFrame(), xCoordinateForAnimation, 0);
        batch.draw(img, endGameRec.x, endGameRec.y, endGameRec.width, endGameRec.height);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(endGameRec.x, endGameRec.y, endGameRec.width, endGameRec.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

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
    }
}
