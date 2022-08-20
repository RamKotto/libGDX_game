package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Application extends ApplicationAdapter {
    SpriteBatch batch;
    int click;
    Anim animation;
    boolean dir = false;
    float x;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new Anim("StormRaiser.png", 5, 5, 1 / 15f, Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 1, 1, 0.5f);
        animation.setTime(Gdx.graphics.getDeltaTime());

//        float x = Gdx.input.getX() - animation.getFrame().getRegionWidth() / 2f;
//        float y = Gdx.graphics.getHeight() - Gdx.input.getY() - animation.getFrame().getRegionHeight() / 2f;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) click++;
        Gdx.graphics.setTitle("Was clicked: " + click + " times! :)");

        float windowWidth = Gdx.graphics.getWidth();

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            dir = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            dir = false;
        }

        if (!animation.getFrame().isFlipX() && !dir) {
            animation.getFrame().flip(true, false);
        }

        if (animation.getFrame().isFlipX() && dir) {
            animation.getFrame().flip(true, false);
        }

        if (!dir) {
            if (x < windowWidth - animation.getFrame().getRegionWidth()) {
                x += 5;
            } else {
                dir = true;
            }
        } else if (dir) {
            if (x > 0) {
                x -= 5;
            } else {
                dir = false;
            }
        }

        batch.begin();
        batch.draw(animation.getFrame(), x, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }
}
