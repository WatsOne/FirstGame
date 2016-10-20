package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends Box {

    private Polygon contactPolygon;

    public Platform(World world, Material material, float x, float y, float h, float w) {
        super(world, material, x, y, h, w);
        contactPolygon = new Polygon(new float[]{0 - 0.1f, 0 - 0.1f, h * 2 + 0.1f, 0 - 0.1f, h * 2 + 0.1f, w * 2 + 0.1f, 0 - 0.1f, w * 2 + 0.1f});
        contactPolygon.setOrigin(h, w);
    }

    @Override
    public void act(float delta) {
        contactPolygon.setPosition(getX(), getY());
        contactPolygon.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        super.act(delta);
    }

    public Polygon getContactPolygon() {
        return contactPolygon;
    }
}
