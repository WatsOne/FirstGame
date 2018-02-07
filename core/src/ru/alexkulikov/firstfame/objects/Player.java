package ru.alexkulikov.firstfame.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.alexkulikov.firstfame.TailDrawer;
import ru.alexkulikov.firstfame.TextureLoader;

public class Player extends GameObject {

    private Polygon contactPolygon;
    private Sprite sprite;
    private TailDrawer tailDrawer;

    public Player(World world) {
        super(world);
    }

    public void createBody(float h, float w) {
        tailDrawer = new TailDrawer(h, w);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2, h / 2);
        Material material = Material.wood;
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(w / 2, h / 2);

        contactPolygon = new Polygon(new float[]{0, 0, w, 0, w, h, 0 , h});
        contactPolygon.setOrigin(w / 2, h / 2);

        sprite = new Sprite(TextureLoader.getPlayer());
        sprite.setBounds(3, 1.5f, 0.4f, 0.4f);
        sprite.setOriginCenter();
    }

    @Override
    public void act(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());

        contactPolygon.setPosition(getX(), getY());
        contactPolygon.setRotation(MathUtils.radiansToDegrees * body.getAngle());

        sprite.setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());

        tailDrawer.update(this);
    }

    public void jump(float power) {
        body.applyLinearImpulse(new Vector2(power * 0.8f, power * 2.5f), body.getPosition(), true);
    }

    public Polygon getContactPolygon() {
        return contactPolygon;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        tailDrawer.draw(getStage().getCamera());
        batch.begin();
        sprite.draw(batch);
    }



    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void clearManual() {
        tailDrawer.clear();
        tailDrawer.dispose();
        remove();
    }
}
