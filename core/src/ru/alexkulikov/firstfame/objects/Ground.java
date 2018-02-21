package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.alexkulikov.firstfame.TextureLoader;

public class Ground extends GameObject {

    private Sprite sprite;

    public Ground(World world) {
        super(world);
        setBounds(50, -5.0f, 200, 1);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100, 0.5f);
        createBody(shape, ObjectType.GROUND, BodyDef.BodyType.StaticBody, 0.4f, 8, 0.2f);

        sprite = new Sprite(TextureLoader.getGround());
        sprite.setBounds(50, -5.0f, 200, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        sprite.setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        super.act(delta);
    }
}
