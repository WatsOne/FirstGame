package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import ru.alexkulikov.firstfame.objects.BoxData;
import ru.alexkulikov.firstfame.objects.ObjectType;

public abstract class BaseLevel {
    protected Group levelGroup;
    protected World world;

    protected List<Rectangle> contactPlatforms;

    public BaseLevel(World world) {
        this.world = world;
        contactPlatforms = new ArrayList<Rectangle>();
    }

    public void buildGroups() {
        levelGroup = new Group();
    }

    public void clearLevel() {
        levelGroup.remove();
        clearBoxesBodies();
    }

    public Group getLevelGroup() {
        return levelGroup;
    }

    private void clearBoxesBodies() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b : bodies) {
            BoxData data = (BoxData) b.getUserData();
            if (data != null && (data.getType() == ObjectType.box || data.getType() == ObjectType.player)) {
                world.destroyBody(b);
            }
        }
    }

    public boolean onPlatform(Rectangle player) {
        for (Rectangle rectangle : contactPlatforms) {
            if (player.overlaps(rectangle)) {
                return true;
            }
        }

        return false;
    }
}
