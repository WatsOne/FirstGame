package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
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

    protected List<Polygon> contactPlatforms;

    public BaseLevel(World world) {
        this.world = world;
        contactPlatforms = new ArrayList<Polygon>();
    }

    public void buildGroups() {
        levelGroup = new Group();
    }

    public void clearLevel() {
        levelGroup.remove();
        contactPlatforms = new ArrayList<Polygon>();
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

    public List<Polygon> getContactPlatforms() {
        return contactPlatforms;
    }

    public boolean onPlatform(Polygon playerPolygon) {
        for (Polygon polygon : contactPlatforms) {
            if (Intersector.overlapConvexPolygons(polygon, playerPolygon)) {
                return true;
            }
        }

        return false;
    }
}
