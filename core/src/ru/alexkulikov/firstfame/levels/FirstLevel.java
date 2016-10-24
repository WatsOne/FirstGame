package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.alexkulikov.firstfame.objects.Box;
import ru.alexkulikov.firstfame.objects.Material;
import ru.alexkulikov.firstfame.objects.Platform;
import com.badlogic.gdx.utils.XmlReader;

import java.io.File;
import java.io.IOException;

public class FirstLevel extends BaseLevel {
    private World world;
    private XmlReader reader;

    public FirstLevel(World world) {
        super(world);
        this.world = world;
        reader= new XmlReader();
    }

    @Override
    public void buildGroups() {
        super.buildGroups();
        try {
            XmlReader.Element root = reader.parse(Gdx.files.internal("levels/level1.xml"));
            float x = Float.parseFloat(root.getAttribute("start"));
            int boxCount = root.getChildCount();
            for (int i = 0; i < boxCount; i++) {
                XmlReader.Element box = root.getChild(i);
                String type = box.getName();

                float shift = Float.parseFloat(box.getAttribute("shift"));
                float y = Float.parseFloat(box.getAttribute("y"));
                float w = Float.parseFloat(box.getAttribute("w"));
                float h = Float.parseFloat(box.getAttribute("h"));
                Material material = Material.valueOf(box.getAttribute("material"));

                if (type.equals("box")) {
                    levelGroup.addActor(new Box(world, material, x + shift, y, w, h));
                } else {
                    Platform platform = new Platform(world, material, x + shift, y, w, h);
                    contactPlatforms.add(platform.getContactPolygon());
                    levelGroup.addActor(platform);
                }
            }
        } catch (IOException e) {
            Gdx.app.log("Pizda", "Pizdec", e);
        }

//        Group platformGroup = new Group();
//        Platform platform;
//        float x = 3;
//
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.2f, 0.4f, 0.4f));
//        platform = new Platform(world, Material.wood, x, 0.6f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 3;
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.4f, 0.4f, 0.8f));
//        platform = new Platform(world, Material.wood, x, 1.0f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 3;
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.6f, 0.4f, 1.2f));
//        platform = new Platform(world, Material.wood, x, 1.4f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 5;
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.6f, 0.4f, 1.2f));
//        platformGroup.addActor(new Box(world, Material.wood, x, 1.8f, 0.4f, 1.2f));
//        platform = new Platform(world, Material.wood, x, 2.6f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 5;
//        platformGroup.addActor(new Box(world, Material.wood, x, 1.0f, 0.4f, 2.0f));
//        platformGroup.addActor(new Box(world, Material.ice, x, 2.2f, 0.4f, 0.4f));
//        platform = new Platform(world, Material.ice, x, 2.6f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 7;
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.6f, 0.4f, 1.2f));
//        platformGroup.addActor(new Box(world, Material.wood, x, 1.4f, 0.4f, 0.4f));
//        platformGroup.addActor(new Box(world, Material.ice, x, 1.8f, 0.4f, 0.4f));
//        platform = new Platform(world, Material.wood, x, 2.2f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
//
//        platformGroup = new Group();
//        x += 4;
//        platformGroup.addActor(new Box(world, Material.wood, x, 0.2f, 0.4f, 0.4f));
//        platform = new Platform(world, Material.wood, x, 0.6f, 2.0f, 0.4f);
//        platformGroup.addActor(platform);
//        contactPlatforms.add(platform.getContactPolygon());
//        levelGroup.addActor(platformGroup);
    }
}
