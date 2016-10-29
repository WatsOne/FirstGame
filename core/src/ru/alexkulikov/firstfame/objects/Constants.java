package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.Gdx;

public class Constants {
    public static final int VIEWPORT_WIDTH = 12;
    public static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH / ((float) Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

    public static final float RATIO_STANDARD = (float) 800/600;
}
