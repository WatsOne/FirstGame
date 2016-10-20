package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;

public class Bound extends ru.alexkulikov.firstfame.objects.GameObject {

    public Bound(World world) {
        super(world);

        float y = 20 / ((float) Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
        setBounds(0, 0, 20, y);

        ChainShape shape = new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0, y + 5), new Vector2(0, 0), new Vector2(20, 0), new Vector2(20, y + 5)});
//        createBody(shape, BodyDef.BodyType.StaticBody, 0.4f, 8, 0.4f);
    }

    @Override
    public void act(float delta) {
    }
}
