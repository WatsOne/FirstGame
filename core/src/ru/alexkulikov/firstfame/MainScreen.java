package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Iterator;

public class MainScreen implements Screen {

    private World world;
    private Stage stage;

    private Box2DDebugRenderer rend;
    private Ball ball;

    private float power;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        float y = 30 / ((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

        rend = new Box2DDebugRenderer();

        stage = new Stage(new FitViewport(30, y));

        stage.addActor(new Ground(world));

        ball = new Ball(world);
        stage.addActor(ball);

        createPlatform(3);
        createPlatform(20);
        createPlatform(37);
        createPlatform(54);
        createPlatform(71);

        stage.setDebugAll(true);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == 'z') {
                    stage.setViewport(new FitViewport(30, 20));
                    ball.jump(-100);
                }
                return true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sweepDeadBodies();
                power = 0;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ball.jump(power);
                Gdx.app.log("Touch up", String.valueOf(power));
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void createPlatform(float x) {
        stage.addActor(new Box(world, x, 1.5f, 0.5f, 0.5f));
        stage.addActor(new Box(world, x, 2.5f,  0.5f, 0.5f));
        stage.addActor(new Box(world, x, 3.5f, 0.5f, 0.5f));
        stage.addActor(new Box(world, x, 4.5f, 0.5f, 0.5f));
        stage.addActor(new Box(world, x, 5.5f, 0.5f, 0.5f));
        stage.addActor(new Box(world, x, 6.5f, 2.5f, 0.5f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rend.render(world, stage.getCamera().combined);
        stage.getCamera().position.set(ball.getX() + 10, ball.getY() + 5, 0);

        if (power < 70f) {
            power++;
        }

        world.step(1/60f, 4, 4);
        stage.act(delta);
        stage.draw();
    }

    public void sweepDeadBodies() {
        Gdx.app.log("Ball position", String.valueOf(ball.getX()));

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b : bodies) {
            BoxData data = (BoxData) b.getUserData();
            if (data != null && data.getType() == ObjectType.box && (ball.getX() - b.getPosition().x > 20)) {
                world.destroyBody(b);
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
