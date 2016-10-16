package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {

    protected Body body;
    private World world;

    public Box(World world) {
        this.world = world;
//        createBody();
    }

    public void createBody(float h, float w) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(h, w);
        fDef.shape = shape;

        fDef.restitution = 0.1f;
        fDef.density = 10;
        fDef.friction = 0.7f;

        body.createFixture(fDef);
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        super.act(delta);
    }
}
