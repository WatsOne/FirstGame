package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TextureLoader {
    private static Texture ice;
    private static Texture wood;
    private static Texture background;
    private static Texture ground;
    private static Texture player;
    private static Texture sky;
    private static Texture mountain;
    private static Texture grass;
    private static Texture cWood;

    public static void load() {
        ice = new Texture(Gdx.files.internal("ice.png"));
        wood = new Texture(Gdx.files.internal("wood.png"));
        background = new Texture(Gdx.files.internal("background.jpg"));
        ground = new Texture(Gdx.files.internal("ground.png"));
        player = new Texture(Gdx.files.internal("player.png"));
        sky = new Texture(Gdx.files.internal("sky.png"));
        mountain = new Texture(Gdx.files.internal("mountain.png"));
        grass = new Texture(Gdx.files.internal("grass.png"));
        cWood = new Texture(Gdx.files.internal("c_wood.png"));
    }

    public static Texture getIce() {
        return ice;
    }

    public static Texture getWood() {
        return wood;
    }

    public static Texture getBackground() {
        return background;
    }

    public static Texture getPlayer() {
        return player;
    }

    public static Texture getGround() {
        return ground;
    }

    public static Texture getSky() {
        return sky;
    }

    public static Texture getMountain() {
        return mountain;
    }

    public static Texture getGrass() {
        return grass;
    }

    public static Texture getcWood() {
        return cWood;
    }
}
