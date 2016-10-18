package ru.alexkulikov.firstfame;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameObject {

    public Player(World world) {
        super(world);
    }

    public void createBody() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f, 0.2f);
        Material material = Material.wood;
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
    }

    public void jump(float power) {
        body.applyLinearImpulse(new Vector2(power * 0.8f, power * 2.5f), body.getPosition(), true);
    }
}
