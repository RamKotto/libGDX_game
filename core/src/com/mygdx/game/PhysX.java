package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Optional;

public class PhysX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    public PhysX() {
        // указываем, что гравитация тянет вниз
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject object, String name) {
        Rectangle rectangle = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        // тело
        BodyDef def = new BodyDef();
        // каркас. Одному телу может быть присвоено несколько фикстуров.
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        def.type = BodyDef.BodyType.valueOf(BodyDef.BodyType.class, type);
        // позиция тела в фзическом движке это середина тела
        def.position.set(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
        def.gravityScale = (float) object.getProperties().get("gravityScale");

        // указываем половину высоты и половину ширины, относительно центра
        polygonShape.setAsBox(rectangle.width / 2, rectangle.height / 2);

        fixtureDef.shape = polygonShape;
        // трение. Если 0 - то это лёд или даже хуже. Больше 7 - 8, трение становится +- одинаковым.
        fixtureDef.friction = 0;
        // плотность
        fixtureDef.density = 1;
        // прыгучесть, упругость. 0 - полностью поглощает инерцию. Если больше - то отскочишь.
        // Если 1 - инерция практически не теряется. Если больше, то отскочшь с большей силой.
        fixtureDef.restitution = (float) object.getProperties().get("restitution");

        Body body;
        body = world.createBody(def);
        body.createFixture(fixtureDef).setUserData(name);

        polygonShape.dispose();
        return body;
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public void step() {
        // сколько времени прошло с предыдущего момента отсчета в секундах;
        // точность расчета скоростей;
        // точность расчета позиций;
        world.step(1 / 60f, 3, 3);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}


//    public void addObject(Rectangle rectangle, String name) {
//        // тело
//        BodyDef def = new BodyDef();
//        // каркас. Одному телу может быть присвоено несколько фикстуров.
//        FixtureDef fixtureDef = new FixtureDef();
//        PolygonShape polygonShape = new PolygonShape();
//
//        def.type = BodyDef.BodyType.StaticBody;
//        // позиция тела в фзическом движке это середина тела
//        def.position.set(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
//        def.gravityScale = 1;
//
//        // указываем половину высоты и половину ширины, относительно центра
//        polygonShape.setAsBox(rectangle.width / 2, rectangle.height / 2);
//
//        fixtureDef.shape = polygonShape;
//        // трение. Если 0 - то это лёд или даже хуже. Больше 7 - 8, трение становится +- одинаковым.
//        fixtureDef.friction = 0;
//        // плотность
//        fixtureDef.density = 1;
//        // прыгучесть, упругость. 0 - полностью поглощает инерцию. Если больше - то отскочишь.
//        // Если 1 - инерция практически не теряется. Если больше, то отскочшь с большей силой.
//        fixtureDef.restitution = 0;
//
//        world.createBody(def).createFixture(fixtureDef).setUserData(name);
//
//        polygonShape.dispose();
//    }
