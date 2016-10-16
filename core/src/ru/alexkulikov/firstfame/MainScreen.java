package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainScreen implements Screen {

    private World world;
    private Stage stage;

    private Box2DDebugRenderer rend;
    private Player player;
    private Ball ball;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        float y = 20 / ((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

        rend = new Box2DDebugRenderer();

        stage = new Stage(new FitViewport(20, y));

        player = new Player(world);

        stage.addActor(new Ground(world));
        stage.addActor(new Bound(world));

        stage.addActor(player);

        ball = new Ball(world);
        stage.addActor(ball);

        createPlatform(3);
        createPlatform(15);

        stage.setDebugAll(true);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setPosition(x, y);
                ball.jump();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                player.setPosition(x, y);
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setPosition(-2, -2);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void createPlatform(float x) {
        Box box1 = new Box(world);
        box1.setBounds(x, 1.5f, 1, 1);
        stage.addActor(box1);
        box1.createBody(0.5f, 0.5f);

        Box box2 = new Box(world);
        box2.setBounds(x, 2.5f, 1, 1);
        stage.addActor(box2);
        box2.createBody(0.5f, 0.5f);

        Box box3 = new Box(world);
        box3.setBounds(x, 3.5f, 1, 1);
        stage.addActor(box3);
        box3.createBody(0.5f, 0.5f);

        Box box4 = new Box(world);
        box4.setBounds(x, 4.5f, 5, 1);
        stage.addActor(box4);
        box4.createBody(2.5f, 0.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rend.render(world, stage.getCamera().combined);
        stage.getCamera().position.set(ball.getX(), ball.getY(), 0);

        world.step(1/60f, 4, 4);
        stage.act(delta);
        stage.draw();
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
