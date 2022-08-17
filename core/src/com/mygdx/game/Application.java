package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Application extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int click;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("ninja.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 1, 1, 0.5f);
        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

        float middleOfImgY = img.getHeight() / 2.0f;
        float middleOfImgX = img.getWidth() / 2.0f;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) click++;

        Gdx.graphics.setTitle("Was clicked: " + click + " times! :)");

        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(img, x - middleOfImgX, y - middleOfImgY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
