package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {

    protected Body body;
    private World world;

    public GameObject(World world) {
        this.world = world;
    }

    protected void createBody(Shape shape, ObjectType oType, BodyDef.BodyType bType, Material material) {
        createBody(shape, oType, bType, material.getRestitution(), material.getDensity(), material.getFriction());
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
        shape.dispose();

        if (oType == ObjectType.SEESAW) {
            BodyDef pin = new BodyDef();
            pin.position.set(getX(), getY());
            pin.type = BodyDef.BodyType.StaticBody;
            Body pinBody = world.createBody(pin);

            RevoluteJointDef joint = new RevoluteJointDef();
            joint.bodyA = body;
            joint.bodyB = pinBody;
            joint.localAnchorA.set(0, 0);
            joint.localAnchorB.set(0, 0);
            joint.collideConnected = false;
            world.createJoint(joint);
        }
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        super.act(delta);
    }
}
