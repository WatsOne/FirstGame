package ru.alexkulikov.firstfame.tail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.alexkulikov.firstfame.tail.simplify.ResolverRadialChaikin;

public class TailPointsProcessor {

    private FixedList<Vector2> inputPoints;

    /** The pointer associated with this swipe event. */
    private int inputPointer = 0;

    /** The minimum distance between the first and second point in a drawn line. */
    public int initialDistance = 10;

    /** The minimum distance between two points in a drawn line (starting at the second point). */
    public int minDistance = 20;

    private Vector2 lastPoint = new Vector2();

    private SwipeResolver simplifier = new ResolverRadialChaikin();
    private Array<Vector2> simplified;

    private float x;

    public TailPointsProcessor(int maxInputPoints) {
        this.inputPoints = new FixedList<Vector2>(maxInputPoints, Vector2.class);
        simplified = new Array<Vector2>(true, maxInputPoints, Vector2.class);
        resolve();
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
//        Gdx.app.log("Path", simplified.toString());
        return simplified;
//        Array<Vector2> p = new Array<Vector2>();
//        p.add(new Vector2(0,0));
//        p.add(new Vector2(10,0));
//        p.add(new Vector2(20,0));
//        p.add(new Vector2(206,449));
//        p.add(new Vector2(192,450));
//        p.add(new Vector2(178,451));
//        p.add(new Vector2(165,453));
//        p.add(new Vector2(153,455));
//        p.add(new Vector2(141,457));
//        p.add(new Vector2(132,458));
//        p.add(new Vector2(126,459));
//        p.add(new Vector2(123,460));
//        p.add(new Vector2(10.2f, 6.25f));
//        p.add(new Vector2(10.2f, 6.27f));
//        p.add(new Vector2(10.2f, 6.28f));
//        p.add(new Vector2(6.0065966f, 5.975439f));
//        p.add(new Vector2(6.0212965f, 5.975439f));
//        p.add(new Vector2(6.1829967f, 5.960739f));
//        p.add(new Vector2(6.4328966f, 5.9313393f));
//        p.add(new Vector2(6.770996f, 5.8872395f));
//        p.add(new Vector2(7.0943966f, 5.857839f));
//        p.add(new Vector2(7.403096f, 5.8137393f));
//        p.add(new Vector2(7.7411966f, 5.754939f));
//        p.add(new Vector2(7.7705965f, 5.754939f));
//        return p;
    }

    /**
     * If the points are dirty, the line is simplified.
     */
    public void resolve() {
        simplifier.resolve(inputPoints, simplified);
    }

    public void initialize(float playerX, float playerY) {
        inputPoints.clear();
        lastPoint = new Vector2(playerX, playerY);
        inputPoints.insert(lastPoint);
        x = playerX;
        resolve();
    }

    public void update(float playerX, float playerY) {
        if (playerX - x < 1) {
            return;
        }

        x = playerX;
        Vector2 v = new Vector2(playerX, playerY);
        inputPoints.insert(v);
        lastPoint = v;
        resolve();
    }
}