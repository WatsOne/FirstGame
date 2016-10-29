package ru.alexkulikov.firstfame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;

public class TailDrawer {
    private FixedList<Vector2> points;
    private Vector2 prevPoint;
    private float playerHeight;
    private float playerWidth;
    private ImmediateModeRenderer20 gl20;
    private Color color;

    public TailDrawer(float playerHeight, float playerWidth) {
        points = new FixedList<Vector2>(70, Vector2.class);
        prevPoint = new Vector2(0,0);

        gl20 = new ImmediateModeRenderer20(false, true, 0);
        color = Color.RED;

        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;
    }

    public void update(Vector2 playerPos) {
        points.insert(playerPos);
        points.insert(new Vector2(playerPos.x, playerPos.y + playerHeight));


        prevPoint = playerPos;
    }

    public void clear() {
        points.clear();
    }

    public void draw(Camera cam) {
        gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP);
        gl20.vertex(prevPoint.x + playerWidth / 2, prevPoint.y + playerHeight / 2, 0f);
        for (int i = 0; i < points.size; i++) {
            Vector2 point = points.get(i);
            gl20.color(color.r, color.g, color.b, (float) (points.size - i)/points.size);
            gl20.vertex(point.x, point.y, 0f);
        }
        gl20.end();
    }

    public void dispose() {
        gl20.dispose();
    }
}
