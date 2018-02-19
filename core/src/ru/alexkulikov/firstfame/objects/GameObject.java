package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.math.MathUtils;
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
        createBody(shape, oType, bType, restitution, density, friction, false);
    }

    protected void createBody(Shape shape, ObjectType oType, BodyDef.BodyType bType, float restitution, float density, float friction, boolean test) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = bType;

        body = world.createBody(bDef);
        if (test) {
            BodyDef bDef2 = new BodyDef();
            bDef2.position.set(getX(), getY());
            bDef2.type = BodyDef.BodyType.StaticBody;

            Body body2 = world.createBody(bDef2);
            RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
//            revoluteJointDef.bodyA = body;
//            revoluteJointDef.bodyB = body2;
            revoluteJointDef.initialize(body, body2, body2.getWorldCenter());
            revoluteJointDef.collideConnected = false;
//            revoluteJointDef.localAnchorA.set(getX(), getY());
//            revoluteJointDef.localAnchorB.set(getX(), getY());
            world.createJoint(revoluteJointDef);
        }
//        }

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
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        super.act(delta);
    }
}
