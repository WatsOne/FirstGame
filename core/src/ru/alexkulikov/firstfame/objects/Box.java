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

    public Box(World world, Material material, float x, float y, float w, float h) {
        super(world);
        setBounds(x, y, w, h);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2, h / 2);
        createBody(shape, ObjectType.box, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(w / 2, h / 2);

        switch (material) {
            case ice:
                sprite = new Sprite(TextureLoader.getIce());
                break;
            case wood:
                sprite = new Sprite(TextureLoader.getWood());
                break;
        }

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
        if (sprite != null) {
            sprite.draw(batch);
        }
    }
}
