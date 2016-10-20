package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ru.alexkulikov.firstfame.levels.BaseLevel;
import ru.alexkulikov.firstfame.levels.FirstLevel;
import ru.alexkulikov.firstfame.objects.BoxData;
import ru.alexkulikov.firstfame.objects.Ground;
import ru.alexkulikov.firstfame.objects.ObjectType;


public class MainScreen implements Screen {

    private World world;
    private Stage stage;

    private Box2DDebugRenderer rend;

    private ru.alexkulikov.firstfame.objects.Player player;
    private BaseLevel currentLevel;

    private float power;
    private GameState state;

    private boolean canJump = true;

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        float y = 12 / ((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

        stage = new Stage(new FitViewport(12, y));

        stage.addActor(new Ground(world));

        currentLevel = new FirstLevel(world);
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

                boolean onPlatform = currentLevel.onPlatform(player.getContactBounds());
                if (canJump || onPlatform) {
                    player.jump(power);
                    canJump = false;
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                ru.alexkulikov.firstfame.objects.BoxData boxDataA = (ru.alexkulikov.firstfame.objects.BoxData) contact.getFixtureA().getBody().getUserData();
                ru.alexkulikov.firstfame.objects.BoxData boxDataB = (BoxData) contact.getFixtureB().getBody().getUserData();

                if (boxDataA != null && boxDataA.getType() == ru.alexkulikov.firstfame.objects.ObjectType.player &&
                        boxDataB != null && boxDataB.getType() == ObjectType.box) {
                    canJump = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
//
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

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

        currentLevel.clearLevel();

        drawLevel();
    }

    private void drawLevel() {
        createPlayer();

        currentLevel.buildGroups();
        stage.addActor(currentLevel.getLevelGroup());

        state = GameState.run;
    }

    private void createPlayer() {
        player = new ru.alexkulikov.firstfame.objects.Player(world);
        player.setBounds(3, 1.5f, 0.4f, 0.4f);
        player.createBody();
        stage.addActor(player);
    }


    private void updateZoom() {
        float y = Math.max(Math.min(player.getY(), 10), 4);
        ((OrthographicCamera) stage.getCamera()).zoom = y/4;

//        ((OrthographicCamera) stage.getCamera()).zoom = 3;
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
