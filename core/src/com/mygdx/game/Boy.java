package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boy {
    private Anim moveAnimation;
    private float X;
    private float Y;
    private float WIDTH = 48;
    private float HEIGHT = 48;
    private float SPEED = 200;

    public Boy() {
        this.moveAnimation = new Anim("atlas/run_atlas.atlas", 1 / 10f, Animation.PlayMode.LOOP);
    }

    public TextureRegion move() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            moveAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (moveAnimation.getFrame().isFlipX()) {
                moveAnimation.getFrame().flip(true, false);
            }
            X += SPEED * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!moveAnimation.getFrame().isFlipX()) {
                moveAnimation.getFrame().flip(true, false);
            }
            X -= SPEED * Gdx.graphics.getDeltaTime();
        }
        return moveAnimation.getFrame();
    }

    public float getX() {
        return this.X;
    }

    public float getY() {
        return this.Y;
    }

    public float getWidth() {
        return this.WIDTH;
    }

    public float getHeight() {
        return this.HEIGHT;
    }

    public void dispose() {
        moveAnimation.dispose();
    }
}
