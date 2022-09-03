package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Boy;
import com.mygdx.game.Main;

public class LevelOneScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture img;
    private Boy player;

    public LevelOneScreen(Main game) {
        batch = new SpriteBatch();
        this.game = game;
        this.player = new Boy();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.YELLOW);

        batch.begin();
        batch.draw(player.move(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        batch.end();

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
        batch.dispose();
        img.dispose();
        player.dispose();
        game.dispose();
    }
}
