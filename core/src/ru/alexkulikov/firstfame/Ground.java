package ru.alexkulikov.firstfame;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends GameObject {

    public Ground(World world) {
        super(world);
        setBounds(10, 0.5f, 20, 1);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 0.5f);
        createBody(shape, BodyDef.BodyType.StaticBody);
    }
}
