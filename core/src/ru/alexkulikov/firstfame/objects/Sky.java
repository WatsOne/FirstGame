package ru.alexkulikov.firstfame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static ru.alexkulikov.firstfame.objects.Constants.*;

import ru.alexkulikov.firstfame.TextureLoader;

public class Sky extends Actor {
    private Sprite sprite;
    float scalableY;
    float x;
    float y;
    float zoom;

    public Sky() {
        setBounds(0, 0, 0, 0);
        sprite = new Sprite(TextureLoader.getSky());
        scalableY = VIEWPORT_WIDTH / RATIO_STANDARD;
        sprite.setBounds(0, 0, 0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, x, y, VIEWPORT_WIDTH * zoom, scalableY * zoom);
    }

    public void update(float camX, float camY, float zoom) {
        x = camX;
        y = camY + VIEWPORT_HEIGHT - scalableY;
        this.zoom = zoom;
        sprite.setPosition(camX, camY);
    }
}
