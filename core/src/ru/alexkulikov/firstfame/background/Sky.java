package ru.alexkulikov.firstfame.background;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static ru.alexkulikov.firstfame.objects.Constants.*;

import ru.alexkulikov.firstfame.TextureLoader;

public class Sky extends Actor {
    private Sprite sprite;
    float scalableY;

    public Sky() {
        setBounds(0, 0, 0, 0);
        sprite = new Sprite(TextureLoader.getSky());
        scalableY = VIEWPORT_WIDTH / RATIO_STANDARD;
        sprite.setBounds(0, 0, VIEWPORT_WIDTH, scalableY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, 0, VIEWPORT_HEIGHT - scalableY, VIEWPORT_WIDTH, scalableY);
    }
}
