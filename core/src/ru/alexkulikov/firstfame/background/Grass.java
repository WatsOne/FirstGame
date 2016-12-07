package ru.alexkulikov.firstfame.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.alexkulikov.firstfame.TextureLoader;

import static ru.alexkulikov.firstfame.objects.Constants.*;

public class Grass extends Actor {
    private Sprite sprite;
    float scalableY;

    public Grass() {
        setBounds(0, 0, 0, 0);
        sprite = new Sprite(TextureLoader.getGrass());
        scalableY = VIEWPORT_WIDTH / RATIO_STANDARD;
        sprite.setBounds(0, 0, 0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, sprite.getX(), -3.6f, scalableY, VIEWPORT_WIDTH);
    }

    public void setSpriteX(float x) {
        sprite.setBounds(x, -3.6f, scalableY, VIEWPORT_WIDTH);
    }

    public float getSpriteOffset() {
        return sprite.getX() + sprite.getWidth();
    }
}
