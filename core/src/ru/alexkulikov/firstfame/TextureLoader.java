package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureLoader {
    private static Texture ice;
    private static Texture wood;
    private static Texture background;
    private static Texture ground;
    private static Texture player;

    public static void load() {
        ice = new Texture(Gdx.files.internal("ice.png"));
        wood = new Texture(Gdx.files.internal("wood.png"));
        background = new Texture(Gdx.files.internal("background.jpg"));
        ground = new Texture(Gdx.files.internal("ground.png"));
        player = new Texture(Gdx.files.internal("player.png"));
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
}