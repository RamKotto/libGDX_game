package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class MenuScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture img;
    private Rectangle startRec;
    private ShapeRenderer shapeRenderer;

    // lesson 6
    private final Music music;
    private final Sound sound;

    public MenuScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("wc3logos/wc3logo.png");
        startRec = new Rectangle(0, 0, 100, 100);
        shapeRenderer = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/remix.mp3"));
        music.setLooping(true);
//        music.setPan(0, 0.5f);
        music.setVolume(0.3f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/toy-shotgun-firing_myx2c5vu.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.draw(img, 0, 0, 100, 100);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(startRec.x, startRec.y, startRec.width, startRec.height);
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (startRec.contains(x, y)) {
                dispose();
                game.setScreen(new LevelOneScreen(game));
            } else {
                sound.play(0.5f);
            }
        }
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
        this.shapeRenderer.dispose();
        this.game.dispose();
        this.music.dispose();
        this.sound.dispose();
    }
}
