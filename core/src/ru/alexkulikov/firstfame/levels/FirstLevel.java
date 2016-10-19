package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.alexkulikov.firstfame.Box;
import ru.alexkulikov.firstfame.Material;

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
        float x = 3;

        platformGroup.addActor(new Box(world, Material.wood, x, 0.7f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 3;
        platformGroup.addActor(new Box(world, Material.wood, x, 0.9f, 0.2f, 0.4f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.5f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 3;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.2f, 0.6f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.9f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 5;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.2f, 0.6f));
        platformGroup.addActor(new Box(world, Material.wood, x, 2.3f, 0.2f, 0.6f));
        platformGroup.addActor(new Box(world, Material.wood, x, 3.1f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 5;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.5f, 0.2f, 1.0f));
        platformGroup.addActor(new Box(world, Material.ice, x, 2.7f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, x, 3.1f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 7;
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 0.2f, 0.6f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.9f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, x, 2.3f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, x, 2.7f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        x += 4;
        platformGroup.addActor(new Box(world, Material.wood, x, 0.7f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, x, 1.1f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);
    }
}
