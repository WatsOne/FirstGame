package ru.alexkulikov.firstfame.objects;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameObject {

    private Polygon contactPolygon;

    public Player(World world) {
        super(world);
    }

    public void createBody(float h, float w) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2, h / 2);
        Material material = Material.wood;
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(w / 2, h / 2);

        contactPolygon = new Polygon(new float[]{0, 0, w, 0, w, h, 0 , h});
        contactPolygon.setOrigin(w / 2, h / 2);
    }

    @Override
    public void act(float delta) {
        contactPolygon.setPosition(getX(), getY());
        contactPolygon.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        super.act(delta);
    }

    public void jump(float power) {
        body.applyLinearImpulse(new Vector2(power * 0.8f, power * 2.5f), body.getPosition(), true);
    }

    public Polygon getContactPolygon() {
        return contactPolygon;
    }
}
