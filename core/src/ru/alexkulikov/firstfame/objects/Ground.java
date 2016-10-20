package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends GameObject {

    public Ground(World world) {
        super(world);
        setBounds(50, 0.0f, 200, 1);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100, 0.5f);
        createBody(shape, ObjectType.ground, BodyDef.BodyType.StaticBody, 0.4f, 8, 0.2f);
    }
}
