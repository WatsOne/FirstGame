package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.alexkulikov.firstfame.TextureLoader;

public class Box extends GameObject {

    private Sprite sprite;

    public Box(World world, Material material, float x, float y, float h, float w) {
        super(world);
        setBounds(x, y, 2 * h, 2 * w);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(h, w);
        createBody(shape, ObjectType.box, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(h, w);

        switch (material) {
            case ice:
                sprite = new Sprite(TextureLoader.getIce());
                break;
            case wood:
                sprite = new Sprite(TextureLoader.getWood());
                break;
        }

        sprite.setBounds(x, y, 2 * h, 2 * w);
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
        if (sprite != null) {
            sprite.draw(batch);
        }
    }
}
