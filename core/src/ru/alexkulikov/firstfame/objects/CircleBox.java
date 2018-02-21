package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.alexkulikov.firstfame.TextureLoader;

public class CircleBox extends GameObject {

    private Sprite sprite;

    public CircleBox(World world, Material material, float x, float y, float r) {
        super(world);
        setBounds(x, y, r, r);
        CircleShape shape = new CircleShape();
        shape.setRadius(r / 2);
        createBody(shape, ObjectType.BOX, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(r / 2, r / 2);

        switch (material) {
//            case ice:
//                sprite = new Sprite(TextureLoader.getIce());
//                break;
            case wood:
                sprite = new Sprite(TextureLoader.getcWood());
                break;
        }

        sprite.setBounds(x, y, r, r);
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