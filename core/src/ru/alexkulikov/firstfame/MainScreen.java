package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

import ru.alexkulikov.firstfame.levels.LevelBuilder;
import ru.alexkulikov.firstfame.levels.LevelBuiltCallback;
import ru.alexkulikov.firstfame.objects.BoxData;
import ru.alexkulikov.firstfame.objects.Ground;
import ru.alexkulikov.firstfame.objects.ObjectType;
import ru.alexkulikov.firstfame.objects.Player;

import static ru.alexkulikov.firstfame.objects.Constants.*;


public class MainScreen implements Screen {

    private World world;
    private Stage stage;

    private Box2DDebugRenderer rend;

    private ru.alexkulikov.firstfame.objects.Player player;
    private LevelBuilder levelBuilder;

    private float power;
    private GameState state;

    private boolean canJump = true;

    private ShapeRenderer shapeRenderer;
    private float y;

    private BackGroundDrawer backGroundDrawer;

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -10), true);
        y = VIEWPORT_HEIGHT / ((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());

        stage = new Stage(new FitViewport(VIEWPORT_WIDTH, y));

        stage.addActor(new Ground(world));

        levelBuilder = new LevelBuilder(world);

        backGroundDrawer = new BackGroundDrawer();
        drawLevel();

        //stage.setDebugAll(true);
        //rend = new Box2DDebugRenderer();
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
                if (state == GameState.gameover) {
                    restart();
                    super.touchUp(event, x, y, pointer, button);
                    return;
                }

                boolean onPlatform = levelBuilder.onPlatform(player.getContactPolygon());
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
                BoxData boxDataA = (BoxData) contact.getFixtureA().getBody().getUserData();
                BoxData boxDataB = (BoxData) contact.getFixtureB().getBody().getUserData();

                if (boxDataA != null && boxDataA.getType() == ObjectType.player &&
                        boxDataB != null && boxDataB.getType() == ObjectType.box) {
                    canJump = true;
                }

                if (boxDataA != null && boxDataA.getType() == ObjectType.ground &&
                        boxDataB != null && boxDataB.getType() == ObjectType.player) {
                    state = GameState.gameover;
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        //rend.render(world, stage.getCamera().combined);

        if (state == GameState.run) {
            stage.getCamera().position.set(player.getX() + 5, Math.min(player.getY() + y/4, y/1.5f), 0);
        }

        if (power < 0.4f) {
            power += 0.005f;
        }

        backGroundDrawer.updateBackGround(player.getX());

        world.step(1/60f, 6, 2);
        stage.act(delta);
        stage.draw();
        updateZoom();

//        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        for (Polygon polygon : currentLevel.getContactPlatforms()) {
//            shapeRenderer.polygon(polygon.getTransformedVertices());
//            shapeRenderer.polygon(player.getContactPolygon().getTransformedVertices());
//        }
//        shapeRenderer.end();
    }

    private void restart() {
        state = GameState.restart;

        player.remove();
        player = null;

        levelBuilder.clearLevel();

        drawLevel();
    }

    private void drawLevel() {
        backGroundDrawer.drawBackGround(stage);

        levelBuilder.buildGroups("level1.xml", new LevelBuiltCallback() {
            @Override
            public void onBuilt(float playerX, float playerY) {
                createPlayer(playerX, playerY);
            }
        });

        stage.addActor(levelBuilder.getLevelGroup());
        state = GameState.run;
    }

    private void createPlayer(float x, float y) {
        player = new Player(world);
        player.setBounds(x, y, 0.4f, 0.4f);
        player.createBody(0.4f, 0.4f);
        stage.addActor(player);
    }


    private void updateZoom() {
        float zoomY = Math.max(Math.min(player.getY(), 10), 1.0f);
        ((OrthographicCamera) stage.getCamera()).zoom = zoomY * 0.08f + 0.9f;
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
