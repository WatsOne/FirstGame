package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.alexkulikov.firstfame.background.GrassDrawer;
import ru.alexkulikov.firstfame.background.MountainDrawer;
import ru.alexkulikov.firstfame.levels.LevelBuilder;
import ru.alexkulikov.firstfame.levels.LevelBuiltCallback;
import ru.alexkulikov.firstfame.objects.BoxData;
import ru.alexkulikov.firstfame.objects.Ground;
import ru.alexkulikov.firstfame.objects.ObjectType;
import ru.alexkulikov.firstfame.background.Sky;
import ru.alexkulikov.firstfame.objects.player.Player;
import ru.alexkulikov.firstfame.objects.player.QuadPlayer;

import static ru.alexkulikov.firstfame.objects.Constants.*;


public class MainScreen implements Screen {

    private World world;
    private Stage stage;
    private Stage backgroundStage;
    private Stage uiStage;

    private Box2DDebugRenderer rend;

    private Player player;
    private LevelBuilder levelBuilder;

    private GameState state;

    private boolean canJump = true;

    private ShapeRenderer shapeRenderer;

    private BitmapFont font;

    private Sky sky;
    private MountainDrawer mountainDrawer;
    private GrassDrawer grassDrawer;

    private boolean leftPressed;
    private boolean rightPressed;

    private ImageButton buttonLeft;
    private ImageButton buttonRight;

    @Override
    public void show() {
        font = new BitmapFont();

        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, -10), true);

        stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        backgroundStage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        uiStage = new Stage(new ScreenViewport());

        sky = new Sky();
        backgroundStage.addActor(sky);

        mountainDrawer = new MountainDrawer(backgroundStage);
        grassDrawer = new GrassDrawer(stage);
        stage.addActor(new Ground(world));

        levelBuilder = new LevelBuilder(world);

        drawLevel();

//        stage.setDebugAll(true);
        rend = new Box2DDebugRenderer();

        InputMultiplexer multiplexer = new InputMultiplexer(uiStage, new KeyGestureDetector(new KeysCallback() {
            @Override
            public void onJump() {
                processJump();
            }
        }, new GestureController(new GestureCallback() {
            @Override
            public void onTouchDown(float x, float y) {
                processJump();
            }
        })));
        Gdx.input.setInputProcessor(multiplexer);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                BoxData boxDataA = (BoxData) contact.getFixtureA().getBody().getUserData();
                BoxData boxDataB = (BoxData) contact.getFixtureB().getBody().getUserData();

                canJump = boxDataA != null && boxDataA.getType() == ObjectType.player &&
                        boxDataB != null && boxDataB.getType() == ObjectType.box;

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

        buttonLeft = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureLoader.getcWood())));
        buttonLeft.setBounds(0,0,200,200);
        buttonLeft.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
                Gdx.app.log("LEFT", "false");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }
        });
        buttonRight = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureLoader.getcWood())));
        buttonRight.setBounds(200, 0, 200, 200);
        buttonRight.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }
        });
        uiStage.addActor(buttonLeft);
        uiStage.addActor(buttonRight);
    }

    private void processJump() {
        if (state == GameState.gameover) {
            restart();
            return;
        }

        boolean onPlatform = levelBuilder.onPlatform(player);
        if (canJump || onPlatform) {
            player.jumpSmall();
            canJump = false;
        }
    }

    private void processMove() {
        if (rightPressed) {
            boolean onPlatform = levelBuilder.onPlatform(player);
            player.move(onPlatform ? 1.0f : 0.3f);
        }

        if (leftPressed) {
            boolean onPlatform = levelBuilder.onPlatform(player);
            player.move(onPlatform ? -1.0f : -0.3f);
        }
    }

    @Override
    public void render(float delta) {
//        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
//        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        processMove();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        if (player.getY() < 0.0f) {
            state = GameState.gameover;
        }

        if (state == GameState.run) {
            stage.getCamera().position.set(player.getX() + 5, Math.min(player.getY() + VIEWPORT_HEIGHT/4, VIEWPORT_HEIGHT/1.5f), 0);
        }

        world.step(1/60f, 6, 2);
        stage.act(delta);
        uiStage.act(delta);

        updateZoom();

        OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
        float camX = camera.position.x;
        mountainDrawer.update(camX - VIEWPORT_WIDTH / 2);
        grassDrawer.update(camX - VIEWPORT_WIDTH / 2);

        backgroundStage.draw();
        stage.draw();
        uiStage.draw();

        shapeRenderer.setProjectionMatrix(uiStage.getCamera().combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(100, 100, 100);
        shapeRenderer.line(75, 100, 125, 100);
        shapeRenderer.line(75, 100, 100, 110);
        shapeRenderer.line(75, 100, 100 ,90);
        shapeRenderer.circle(300, 100, 100);
        shapeRenderer.line(275, 100, 325, 100);
        shapeRenderer.line(325, 100, 300, 110);
        shapeRenderer.line(325, 100, 300 ,90);
        shapeRenderer.end();

//        shapeRenderer.setProjectionMatrix(backgroundStage.getCamera().combined);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        for (Polygon polygon : levelBuilder.getContactPlatforms()) {
//            shapeRenderer.polygon(polygon.getTransformedVertices());
//            shapeRenderer.polygon(((QuadPlayer) player).getContactShape().getTransformedVertices());
//        }

//        shapeRenderer.rect(0.5f, 0.4f, lineFill, 0.2f);
//        shapeRenderer.end();
//
//        shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.rect(0.5f, 0.4f, 4, 0.2f);
//        shapeRenderer.end();

//        backgroundStage.getBatch().begin();
//        font.draw(backgroundStage.getBatch(), String.valueOf(power), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
//        backgroundStage.getBatch().end();

//        rend.render(world, stage.getCamera().combined);

    }

    private void restart() {
        state = GameState.restart;

        player.clearManual();
        player = null;

        levelBuilder.clearLevel();

        drawLevel();
    }

    private void drawLevel() {
        mountainDrawer.initialize();
        grassDrawer.initialize();

        levelBuilder.buildGroups("level3.xml", new LevelBuiltCallback() {
            @Override
            public void onBuilt(float playerX, float playerY) {
                createPlayer(playerX, playerY);
            }
        });

        stage.addActor(levelBuilder.getLevelGroup());
        state = GameState.run;
    }

    private void createPlayer(float x, float y) {
//        player = new CirclePlayer(world, x, y, 0.4f);
        player = new QuadPlayer(world, x, y, 0.4f, 0.4f);
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
        stage.dispose();
        backgroundStage.dispose();
        uiStage.dispose();
        world.dispose();
    }
}
