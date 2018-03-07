package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box extends GameObject {

    private Sprite sprite;

    public Box(Texture texture, World world, Material material, float x, float y, float w, float h) {
        this(texture, world, ObjectType.BOX, material, x, y, w, h, BodyDef.BodyType.DynamicBody);
    }

    public Box(Texture texture, World world, Material material, float x, float y, float w, float h, BodyDef.BodyType type) {
        this(texture, world, ObjectType.BOX, material, x, y, w, h, type);
    }

    public Box(Texture texture, World world, ObjectType oType, Material material, float x, float y, float w, float h) {
        this(texture, world, oType, material, x, y, w, h, BodyDef.BodyType.DynamicBody);
    }

    private Box(Texture texture, World world, ObjectType oType, Material material, float x, float y, float w, float h, BodyDef.BodyType type) {
        super(world);

        setBounds(x, y, w, h);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2, h / 2);
        createBody(shape, oType, type, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(w / 2, h / 2);

        sprite = new Sprite(texture);
        sprite.setU(-(w/h-1));
        sprite.setBounds(x, y, w, h);
        sprite.setOriginCenter();
    }

    @Override
    public void act(float delta) {
        if (sprite != null) {
            sprite.setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
            sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        }

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (sprite != null) {
            sprite.draw(batch);
        }
    }
}
