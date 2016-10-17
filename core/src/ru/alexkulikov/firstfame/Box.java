package ru.alexkulikov.firstfame;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box extends GameObject {
    public Box(World world, float x, float y, float h, float w) {
        super(world);
        setBounds(x, y, 2 * h, 2 * w);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(h, w);
        createBody(shape, ObjectType.box, BodyDef.BodyType.DynamicBody, 0.1f, 10, 0.9f);
    }
}
