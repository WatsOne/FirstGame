package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;

import ru.alexkulikov.firstfame.objects.player.Player;
import ru.alexkulikov.firstfame.objects.player.QuadPlayer;

public class TailDrawer {
    private FixedList<Vector2> points;
    private float playerHeight;
    private float playerWidth;
    private ImmediateModeRenderer20 gl20;
    private Color color;

    public TailDrawer(float playerHeight, float playerWidth) {
        points = new FixedList<Vector2>(70, Vector2.class);

        gl20 = new ImmediateModeRenderer20(false, true, 0);
        color = Color.CYAN;

        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;
    }

    public void clear() {
        points.clear();
    }

    public void update(Player player) {
        Vector2 velocity = player.getLinearVelocity();
        float len = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);

        float x = player.getX() + playerWidth / 2 + velocity.y / len / 5;
        float y = player.getY() + playerHeight / 2 - velocity.x / len / 5;

        points.insert(new Vector2(x,y));

        x = player.getX() + playerWidth / 2 - velocity.y / len / 5;
        y = player.getY() + playerHeight / 2 + velocity.x / len / 5;

        points.insert(new Vector2(x,y));
    }

    public void draw(Camera cam) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP);
        for (int i = 0; i < points.size; i++) {
            Vector2 point = points.get(i);
            gl20.color(color.r, color.g, color.b, (float) (points.size - i) / points.size);
            gl20.vertex(point.x, point.y, 0f);
        }
        gl20.end();
    }

    public void dispose() {
        gl20.dispose();
    }
}
