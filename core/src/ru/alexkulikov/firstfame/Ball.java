package ru.alexkulikov.firstfame;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ball extends GameObject {

    public Ball(World world) {
        super(world);
        setBounds(3, 4.5f, 1, 1);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);
        createBody(shape, BodyDef.BodyType.DynamicBody);
//        body.getFixtureList().get(0).setDensity(15);
    }

    public void jump() {
        body.applyLinearImpulse(new Vector2(70,90), body.getPosition(),true);
    }
}
