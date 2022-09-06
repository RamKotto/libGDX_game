package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Boy {
    private Anim moveAnimation;
    private float FRAME_DURATION = 1 / 10f;
    private float x;
    private float y;
    private float width;
    private float height;
    private Body body;
    private Rectangle rectangle;
    private float SPEED = 200;

    public Boy(RectangleMapObject rectangleMapObject, Body body) {
        this.moveAnimation = new Anim("atlas/run_atlas.atlas", FRAME_DURATION, Animation.PlayMode.LOOP);
        // ширину и высотуполучаем из Rectangle из карты
        this.rectangle = rectangleMapObject.getRectangle();
        this.height = rectangleMapObject.getRectangle().height;
        this.width = rectangleMapObject.getRectangle().width;
        // позицию по x и y получаем от тела
        this.body = body;
        this.x = body.getPosition().x - width / 2;
        this.y = body.getPosition().y - height / 2;
    }

    public TextureRegion move() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            moveAnimation.setTime(Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (moveAnimation.getFrame().isFlipX()) {
                moveAnimation.getFrame().flip(true, false);
            }
            body.applyForceToCenter(new Vector2(1_000_000, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!moveAnimation.getFrame().isFlipX()) {
                moveAnimation.getFrame().flip(true, false);
            }
            body.applyForceToCenter(new Vector2(-1_000_000, 0), true);
        }
        this.setX(body.getPosition().x - width / 2);
        this.setY(body.getPosition().y - height / 2);
        return moveAnimation.getFrame();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void dispose() {
        moveAnimation.dispose();
    }
}
