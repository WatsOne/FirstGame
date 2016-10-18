package ru.alexkulikov.firstfame;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

public class PlatformBuilder {

    public static void buildT(Group group, World world, float x, Material material) {
        group.addActor(new Box(world, material, x, 1.5f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 1.9f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 2.3f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 2.8f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 3.2f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 3.6f, 1.0f, 0.2f));
    }

    public static void buildP(Group group, World world, float x, Material material) {
        group.addActor(new Box(world, material, x, 1.5f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 1.9f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 2.3f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 2.8f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x, 3.2f, 0.2f, 0.2f));

        group.addActor(new Box(world, material, x + 1.5f, 1.5f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x + 1.5f, 1.9f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x + 1.5f, 2.3f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x + 1.5f, 2.8f, 0.2f, 0.2f));
        group.addActor(new Box(world, material, x + 1.5f, 3.2f, 0.2f, 0.2f));

        group.addActor(new Box(world, material, x + 0.75f, 3.6f, 2.0f, 0.2f));
    }
}
