package ru.alexkulikov.firstfame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ru.alexkulikov.firstfame.background.GrassDrawer
import ru.alexkulikov.firstfame.background.MountainDrawer
import ru.alexkulikov.firstfame.background.Sky
import ru.alexkulikov.firstfame.levels.LevelBuilder2
import ru.alexkulikov.firstfame.objects.Constants.VIEWPORT_HEIGHT
import ru.alexkulikov.firstfame.objects.Constants.VIEWPORT_WIDTH
import ru.alexkulikov.firstfame.objects.Ground
import ru.alexkulikov.firstfame.objects.player.Player
import ru.alexkulikov.firstfame.objects.player.QuadPlayer

class MainScreen2(private val debugMode: Boolean, private val desktopMode: Boolean) : Screen {

    private lateinit var world: World
    private lateinit var mainStage: Stage
    private lateinit var backgroundStage: Stage
    private lateinit var uiStage: Stage

    private lateinit var stageRenderer: ShapeRenderer
    private lateinit var worldRenderer: Box2DDebugRenderer

    private lateinit var mountainDrawer: MountainDrawer
    private lateinit var grassDrawer: GrassDrawer

    private lateinit var player: Player
    private lateinit var gameState: GameState
    private lateinit var levelBuilder: LevelBuilder2

    private var canJump = false
    private var leftPressed = false
    private var rightPressed = false

    override fun show() {
        world = World(Vector2(0f, -10f), true)

        mainStage = Stage(FitViewport(VIEWPORT_WIDTH.toFloat(), VIEWPORT_HEIGHT))
        backgroundStage = Stage(FitViewport(VIEWPORT_WIDTH.toFloat(), VIEWPORT_HEIGHT))
        uiStage = Stage(ScreenViewport())

        if (debugMode) {
            stageRenderer = ShapeRenderer()
            worldRenderer = Box2DDebugRenderer()

            mainStage.isDebugAll = true
        } else {
            initBackGround()
            grassDrawer = GrassDrawer(mainStage)
        }

        levelBuilder = LevelBuilder2(world)
        world.setContactListener(WorldContactListener( { canJump = it }, { gameState = GameState.gameover } ))
        Gdx.input.inputProcessor = InputMultiplexer(mainStage, KeyGestureDetector(this::processJump, GestureController(this::processJump)))

        drawLevel()
    }

    private fun initBackGround() {
        backgroundStage.addActor(Sky())
        backgroundStage.addActor(Ground(world))
        mountainDrawer = MountainDrawer(backgroundStage)
    }

    private fun drawLevel() {
        if (!debugMode) {
            mountainDrawer.initialize()
            grassDrawer.initialize()
        }

        levelBuilder.build("level3.xml", this::createPlayer)
        mainStage.addActor(levelBuilder.levelGroup)
        gameState = GameState.run
    }

    private fun createPlayer(x: Float, y: Float) {
        player = QuadPlayer(world, x, y, 0.4f, 0.4f)
        mainStage.addActor(player)
    }

    private fun restart() {
        gameState = GameState.restart
        player.clearManual()
        levelBuilder.clearLevel()

        drawLevel()
    }

    private fun updateZoom() {
        val zoomY = Math.max(Math.min(player.y, 10f), 1.0f)
        (mainStage.camera as OrthographicCamera).zoom = zoomY * 0.08f + 0.9f
    }

    private fun processJump() {
        if (gameState == GameState.gameover) {
            restart()
            return
        }

        val onPlatform = levelBuilder.onPlatform(player)
        if (canJump || onPlatform) {
            player.jumpSmall()
            canJump = false
        }
    }

    private fun processMove() {
        if (rightPressed) {
            val onPlatform = levelBuilder.onPlatform(player)
            player.move(if (onPlatform) 1.0f else 0.3f)
        }

        if (leftPressed) {
            val onPlatform = levelBuilder.onPlatform(player)
            player.move(if (onPlatform) -1.0f else -0.3f)
        }
    }

    override fun render(delta: Float) {
        if (desktopMode) {
            leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT)
            rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)

        if (player.y < 0.0f) {
            gameState = GameState.gameover
        }

        if (gameState == GameState.run) {
            mainStage.camera.position.set(player.x + 5, Math.min(player.y + VIEWPORT_HEIGHT / 4, VIEWPORT_HEIGHT / 1.5f), 0f)

        }

        world.step(1 / 60f, 6, 2)
        mainStage.act(delta)
        uiStage.act(delta)

        processMove()
        updateZoom()

        if (debugMode) {
            worldRenderer.render(world, mainStage.camera.combined)
            mainStage.camera.update()
        } else {
            val camX = (mainStage.camera as OrthographicCamera).position.x
            mountainDrawer.update(camX - VIEWPORT_WIDTH / 2)
            grassDrawer.update(camX - VIEWPORT_WIDTH / 2)

            backgroundStage.draw()
            mainStage.draw()
            uiStage.draw()
        }
    }

    override fun hide() = Unit
    override fun pause() = Unit
    override fun resume() = Unit
    override fun resize(width: Int, height: Int) = Unit

    override fun dispose() {
        mainStage.dispose()
        backgroundStage.dispose()
        uiStage.dispose()
        world.dispose()
    }
}