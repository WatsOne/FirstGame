package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ru.alexkulikov.firstfame.PolygonUtils;
import ru.alexkulikov.firstfame.objects.Box;
import ru.alexkulikov.firstfame.objects.BoxData;
import ru.alexkulikov.firstfame.objects.Material;
import ru.alexkulikov.firstfame.objects.ObjectType;
import ru.alexkulikov.firstfame.objects.Platform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {

    private World world;
    private XmlReader reader;
    private List<Polygon> contactPlatforms;
    private Group levelGroup;
    private PolygonUtils polygonUtils;

    public LevelBuilder(World world) {
        this.world = world;
        reader= new XmlReader();
        polygonUtils = new PolygonUtils();
    }

    public void buildGroups(String levelName, LevelBuiltCallback callback) {
        levelGroup = new Group();
        contactPlatforms = new ArrayList<Polygon>();

//        try {
            XmlReader.Element root = reader.parse(Gdx.files.internal("levels/" + levelName));
            XmlReader.Element player = root.getChildByName("player");

            float playerX = Float.parseFloat(player.getAttribute("x"));
            float playerY = Float.parseFloat(player.getAttribute("y"));

            XmlReader.Element boxes = root.getChildByName("boxes");
            int boxCount = boxes.getChildCount();
            for (int i = 0; i < boxCount; i++) {
                XmlReader.Element box = boxes.getChild(i);
                String type = box.getName();

                float x = Float.parseFloat(box.getAttribute("x"));
                float y = Float.parseFloat(box.getAttribute("y"));
                float w = Float.parseFloat(box.getAttribute("w"));
                float h = Float.parseFloat(box.getAttribute("h"));
                Material material = Material.valueOf(box.getAttribute("material"));

                if (type.equals("box")) {
                    levelGroup.addActor(new Box(world, material, x, y, w, h));
                } else {
                    Platform platform = new Platform(world, material, x, y, w, h);
                    contactPlatforms.add(platform.getContactPolygon());
                    levelGroup.addActor(platform);
                }
            }

            callback.onBuilt(playerX, playerY);

//        } catch (IOException e) {
//            Gdx.app.log("Level builder", "Cannot parse xml", e);
//        }
    }

    public void clearBoxesBodies() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b : bodies) {
            BoxData data = (BoxData) b.getUserData();
            if (data != null && (data.getType() == ObjectType.box || data.getType() == ObjectType.player)) {
                world.destroyBody(b);
            }
        }
    }

    public void clearLevel() {
        levelGroup.remove();
        contactPlatforms = new ArrayList<Polygon>();
        clearBoxesBodies();
    }

    public boolean onPlatform(Polygon playerPolygon) {
        for (Polygon polygon : contactPlatforms) {
            if (Intersector.overlapConvexPolygons(polygon, playerPolygon)) {
                return true;
            }
        }

        return false;
    }

    public boolean onPlatform(Circle playerPolygon) {
        for (Polygon polygon : contactPlatforms) {
            if (polygonUtils.overlaps(polygon, playerPolygon)) {
                return true;
            }
        }

        return false;
    }

    public Group getLevelGroup() {
        return levelGroup;
    }
}
