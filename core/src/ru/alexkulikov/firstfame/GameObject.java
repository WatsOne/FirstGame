package ru.alexkulikov.firstfame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {

    protected Body body;
    private World world;

    public GameObject(World world) {
        this.world = world;
    }

    protected void createBody(Shape shape, BodyDef.BodyType type) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = type;

        body = world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.restitution = 0.4f;
        fDef.density = 8;
        fDef.friction = 0.4f;

        body.createFixture(fDef);
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        super.act(delta);
    }
}
