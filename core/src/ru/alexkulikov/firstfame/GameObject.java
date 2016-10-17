package ru.alexkulikov.firstfame;

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

    protected void createBody(Shape shape, ObjectType oType, BodyDef.BodyType bType, float restitution, float density, float friction) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = bType;

        body = world.createBody(bDef);

        body.setUserData(new BoxData(oType));

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.restitution = restitution;
        fDef.density = density;
        fDef.friction = friction;

        body.createFixture(fDef);
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        super.act(delta);
    }
}
