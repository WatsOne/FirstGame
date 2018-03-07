package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import ru.alexkulikov.firstfame.Path;
import ru.alexkulikov.firstfame.TextureLoader;

public class Box extends GameObject {

    private Sprite sprite;
    private List<Sprite> spriteList;

    public Box(AssetManager manager, World world, Material material, float x, float y, float w, float h) {
        this(manager, world, ObjectType.BOX, material, x, y, w, h, BodyDef.BodyType.DynamicBody);
    }

    public Box(AssetManager manager, World world, Material material, float x, float y, float w, float h, BodyDef.BodyType type) {
        this(manager, world, ObjectType.BOX, material, x, y, w, h, type);
    }

    public Box(AssetManager manager, World world, ObjectType oType, Material material, float x, float y, float w, float h) {
        this(manager, world, oType, material, x, y, w, h, BodyDef.BodyType.DynamicBody);
    }

    private Box(AssetManager manager, World world, ObjectType oType, Material material, float x, float y, float w, float h, BodyDef.BodyType type) {
        super(world);

        setBounds(x, y, w, h);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2, h / 2);
        createBody(shape, oType, type, material.getRestitution(), material.getDensity(), material.getFriction());
        setOrigin(w / 2, h / 2);

        switch (material) {
            case ice:
                sprite = new Sprite(TextureLoader.getIce());
                sprite.setBounds(x, y, w, h);
                sprite.setOriginCenter();
                break;
            case wood:
                Texture woodTexture = manager.get(Path.woodMaterial);

                int countSprites = Math.round(getWidth() / getHeight());

                spriteList = new ArrayList<>();
                Sprite s;
                for (int i = 0; i < countSprites; i++) {
                    s = new Sprite(woodTexture);
                    s.setOrigin(w/2 - (i*h), h/2);
                    s.setBounds(x + i*h, y, h, h);
                    spriteList.add(s);
                }
                break;
        }
    }

    @Override
    public void act(float delta) {
        if (sprite != null) {
            sprite.setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
            sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        }
        if (spriteList != null) {
            Sprite s;
            for (int i = 0; i < spriteList.size(); i++) {
                s = spriteList.get(i);
                s.setPosition(body.getPosition().x - getWidth()/2 + i*getHeight(), body.getPosition().y - getHeight()/2);
                s.setRotation(MathUtils.radiansToDegrees * body.getAngle());
            }
        }

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (sprite != null) {
            sprite.draw(batch);
        }

        if (spriteList != null) {
            for (Sprite s : spriteList) {
                s.draw(batch);
            }
        }
    }
}
