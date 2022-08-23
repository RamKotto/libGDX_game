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
    boolean lookRight = true;
    float x;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new Anim("atlas/run_atlas.atlas", 1 / 10f, Animation.PlayMode.LOOP);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) lookRight = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) lookRight = true;
        if (x >= windowWidth - animation.getFrame().getRegionWidth()) lookRight = false;
        if (x <= 0) lookRight = true;

        if (animation.getFrame().isFlipX() && lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!animation.getFrame().isFlipX() && !lookRight) {
            animation.getFrame().flip(true, false);
        }

        if (!lookRight) {
            x -= 5;
        } else {
            x += 5;
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
