package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.alexkulikov.firstfame.objects.Box;
import ru.alexkulikov.firstfame.objects.Material;
import ru.alexkulikov.firstfame.objects.Platform;

public class FirstLevel extends BaseLevel {
    private World world;

    public FirstLevel(World world) {
        super(world);
        this.world = world;
    }

    @Override
    public void buildGroups() {
        super.buildGroups();
        Group platformGroup = new Group();
        Platform platform;
        float x = 3;

        platformGroup.addActor(new Box(world, Material.wood, x, 0.7f, 0.4f, 0.4f));
        platform = new Platform(world, Material.wood, x, 1.1f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 3;
        platformGroup.addActor(new Box(world, Material.wood, x, 0.9f, 0.4f, 0.8f));
        platform = new Platform(world, Material.wood, x, 1.5f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 3;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.4f, 1.2f));
        platform = new Platform(world, Material.wood, x, 1.9f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 5;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.4f, 1.2f));
        platformGroup.addActor(new Box(world, Material.wood, x, 2.3f, 0.4f, 1.2f));
        platform = new Platform(world, Material.wood, x, 3.1f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 5;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.5f, 0.4f, 2.0f));
        platformGroup.addActor(new Box(world, Material.ice, x, 2.7f, 0.4f, 0.4f));
        platform = new Platform(world, Material.ice, x, 3.1f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 7;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.4f, 1.2f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.9f, 0.4f, 0.4f));
        platformGroup.addActor(new Box(world, Material.ice, x, 2.3f, 0.4f, 0.4f));
        platform = new Platform(world, Material.wood, x, 2.7f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 4;
        platformGroup.addActor(new Box(world, Material.wood, x, 0.7f, 0.4f, 0.4f));
        platform = new Platform(world, Material.wood, x, 1.1f, 2.0f, 0.4f);
        platformGroup.addActor(platform);
        contactPlatforms.add(platform.getContactPolygon());
        levelGroup.addActor(platformGroup);
    }
}
