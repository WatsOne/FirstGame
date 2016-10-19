package ru.alexkulikov.firstfame.levels;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

import ru.alexkulikov.firstfame.Box;
import ru.alexkulikov.firstfame.Material;

public class FirstLevel extends BaseLevel {
    private World world;

    private static float X_POS = 3;

    public FirstLevel(World world) {
        super(world);

        this.world = world;
    }

    @Override
    public void buildGroups() {
        super.buildGroups();
        Group platformGroup = new Group();

        platformGroup.addActor(new Box(world, Material.wood, X_POS, 1.5f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, X_POS, 1.9f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, X_POS, 2.3f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, X_POS, 2.8f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, X_POS, 3.2f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.wood, X_POS, 3.6f, 1.0f, 0.2f));
        levelGroup.addActor(platformGroup);

        platformGroup = new Group();
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7, 1.5f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7, 1.9f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7, 2.3f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7, 2.8f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7, 3.2f, 0.2f, 0.2f));

        platformGroup.addActor(new Box(world, Material.ice, X_POS + 8.5f, 1.5f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 8.5f, 1.9f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 8.5f, 2.3f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 8.5f, 2.8f, 0.2f, 0.2f));
        platformGroup.addActor(new Box(world, Material.ice, X_POS + 8.5f, 3.2f, 0.2f, 0.2f));

        platformGroup.addActor(new Box(world, Material.ice, X_POS + 7.75f, 3.6f, 2.0f, 0.2f));
        levelGroup.addActor(platformGroup);
    }
}
