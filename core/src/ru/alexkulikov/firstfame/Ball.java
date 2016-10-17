package ru.alexkulikov.firstfame;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ball extends GameObject {

    public Ball(World world) {
        super(world);
        setBounds(3, 6.5f, 1, 1);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, 0.4f, 8, 0.4f);
    }

    public void jump(float power) {
        body.applyLinearImpulse(new Vector2(power * 0.8f, power * 1.4f), body.getPosition(),true);
    }
}
