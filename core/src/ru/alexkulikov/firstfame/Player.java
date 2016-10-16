package ru.alexkulikov.firstfame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameObject {

    public Player(World world) {
        super(world);
        setPosition(-2, -2);
        CircleShape shape = new CircleShape();
        shape.setRadius(2);
        createBody(shape, BodyDef.BodyType.KinematicBody);
    }

    @Override
    public void act(float delta) {
        body.setTransform(getX(), getY(), 0);
        super.act(delta);
    }
}
