package ru.alexkulikov.firstfame.tail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.alexkulikov.firstfame.tail.simplify.ResolverRadialChaikin;

public class SwipeHandler extends InputAdapter {

    private FixedList<Vector2> inputPoints;

    /** The pointer associated with this swipe event. */
    private int inputPointer = 0;

    /** The minimum distance between the first and second point in a drawn line. */
    public int initialDistance = 10;

    /** The minimum distance between two points in a drawn line (starting at the second point). */
    public int minDistance = 20;

    private Vector2 lastPoint = new Vector2();

    private boolean isDrawing = false;

    private SwipeResolver simplifier = new ResolverRadialChaikin();
    private Array<Vector2> simplified;

    public SwipeHandler(int maxInputPoints) {
        this.inputPoints = new FixedList<Vector2>(maxInputPoints, Vector2.class);
        simplified = new Array<Vector2>(true, maxInputPoints, Vector2.class);
        resolve(); //copy initial empty list
    }

    /**
     * Returns the fixed list of input points (not simplified).
     * @return the list of input points
     */
    public Array<Vector2> input() {
        return this.inputPoints;
    }

    /**
     * Returns the simplified list of points representing this swipe.
     * @return
     */
    public Array<Vector2> path() {
//        return simplified;
        Array<Vector2> p = new Array<Vector2>();
        p.add(new Vector2(0,0));
        p.add(new Vector2(10,0));
        p.add(new Vector2(20,0));
//        p.add(new Vector2(206,449));
//        p.add(new Vector2(192,450));
//        p.add(new Vector2(178,451));
//        p.add(new Vector2(165,453));
//        p.add(new Vector2(153,455));
//        p.add(new Vector2(141,457));
//        p.add(new Vector2(132,458));
//        p.add(new Vector2(126,459));
//        p.add(new Vector2(123,460));
//        p.add(new Vector2(10.2f / 2, 6.25f/3 ));
//        p.add(new Vector2(10.2f / 2,6.5f /3));
//        p.add(new Vector2(10.2f / 2,6.75f /3));
//        p.add(new Vector2(6.0065966f, 5.975439f));
//        p.add(new Vector2(6.0212965f, 5.975439f));
//        p.add(new Vector2(6.1829967f, 5.960739f));
//        p.add(new Vector2(6.4328966f, 5.9313393f));
//        p.add(new Vector2(6.770996f, 5.8872395f));
//        p.add(new Vector2(7.0943966f, 5.857839f));
//        p.add(new Vector2(7.403096f, 5.8137393f));
//        p.add(new Vector2(7.7411966f, 5.754939f));
//        p.add(new Vector2(7.7705965f, 5.754939f));
        return p;
    }

    /**
     * If the points are dirty, the line is simplified.
     */
    public void resolve() {
        simplifier.resolve(inputPoints, simplified);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer!=inputPointer)
            return false;
        isDrawing = true;

        //clear points
        inputPoints.clear();

        //starting point
        lastPoint = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);
        inputPoints.insert(lastPoint);

        resolve();
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //on release, the line is simplified
        resolve();
        isDrawing = false;
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.log("XXX", screenX + " " + screenY);

        if (pointer!=inputPointer)
            return false;
        isDrawing = true;

        Vector2 v = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);

        //calc length
        float dx = v.x - lastPoint.x;
        float dy = v.y - lastPoint.y;
        float len = (float)Math.sqrt(dx*dx + dy*dy);
        //TODO: use minDistanceSq

        //if we are under required distance
        if (len < minDistance && (inputPoints.size>1 || len<initialDistance))
            return false;

        //add new point
        inputPoints.insert(v);

        lastPoint = v;

        //simplify our new line
        resolve();
        return true;
    }
}