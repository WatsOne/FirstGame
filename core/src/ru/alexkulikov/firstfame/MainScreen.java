package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainScreen implements Screen {

    private World world;
    private Stage stage;

    private Box2DDebugRenderer rend;

    private Player player;
    private Group boxesGroup;

    private float power;
    private GameState state;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        float y = 12 / ((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

        stage = new Stage(new FitViewport(12, y));

        stage.addActor(new Ground(world));

        drawLevel();

        stage.setDebugAll(true);
        rend = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == 'c') {
                    restart();
                }

                return true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                power = 0.2f;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.jump(power);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rend.render(world, stage.getCamera().combined);

        if (state == GameState.run) {
            stage.getCamera().position.set(player.getX() + 5, player.getY() + 1, 0);
        }

        if (power < 0.4f) {
            power += 0.005f;
        }

        world.step(1/60f, 6, 2);
        stage.act(delta);
        stage.draw();
        updateZoom();
    }

    private void restart() {
        state = GameState.restart;

        player.remove();
        player = null;

        clearBoxes();
        drawLevel();
    }

    private void drawLevel() {
        createPlayer();

        boxesGroup = new Group();
        stage.addActor(boxesGroup);

        PlatformBuilder.buildT(boxesGroup, world, 3, Material.wood);
        PlatformBuilder.buildP(boxesGroup, world, 7, Material.ice);

        state = GameState.run;
    }

    private void createPlayer() {
        player = new Player(world);
        player.setBounds(3, 4.0f, 0.4f, 0.4f);
        player.createBody();
        stage.addActor(player);
    }

    private void clearBoxes() {
        boxesGroup.remove();
        boxesGroup = null;
        clearBoxesBodies();
    }

    public void clearBoxesBodies() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b : bodies) {
            BoxData data = (BoxData) b.getUserData();
            if (data != null && (data.getType() == ObjectType.box || data.getType() == ObjectType.player)) {
                world.destroyBody(b);
            }
        }
    }

    private void updateZoom() {
        float y = Math.max(Math.min(player.getY(), 10), 4);
        ((OrthographicCamera) stage.getCamera()).zoom = y/4;
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
