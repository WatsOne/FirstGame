package ru.alexkulikov.firstfame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.inject.Context
import ru.alexkulikov.firstfame.*
import ru.alexkulikov.firstfame.background.GrassDrawer
import ru.alexkulikov.firstfame.background.MountainDrawer
import ru.alexkulikov.firstfame.background.Sky
import ru.alexkulikov.firstfame.levels.LevelBuilder
import ru.alexkulikov.firstfame.levels.ZoomManager
import ru.alexkulikov.firstfame.objects.Constants.VIEWPORT_HEIGHT
import ru.alexkulikov.firstfame.objects.Constants.VIEWPORT_WIDTH
import ru.alexkulikov.firstfame.objects.Ground
import ru.alexkulikov.firstfame.objects.player.Player

class GameScreen(private val context: Context, private val debugMode: Boolean, private val desktopMode: Boolean) : Screen {

    private lateinit var world: World
    private lateinit var mainStage: Stage
    private lateinit var backgroundStage: Stage
    private lateinit var uiStage: UiStage

    private lateinit var stageRenderer: ShapeRenderer
    private lateinit var worldRenderer: Box2DDebugRenderer
    //private val frameRate = FrameRate()

    private lateinit var mountainDrawer: MountainDrawer
    private lateinit var grassDrawer: GrassDrawer

    private lateinit var player: Player
    private lateinit var gameState: GameState
    private lateinit var levelBuilder: LevelBuilder
    private val zoomManager = ZoomManager()

    private var leftPressed = false
    private var rightPressed = false

    override fun show() {
        world = World(Vector2(0f, -10f), true)

        mainStage = Stage(FitViewport(VIEWPORT_WIDTH.toFloat(), VIEWPORT_HEIGHT))
        backgroundStage = Stage(FitViewport(VIEWPORT_WIDTH.toFloat(), VIEWPORT_HEIGHT))
        uiStage = UiStage(ScreenViewport(), context.inject(), debugMode, desktopMode)

        if (debugMode) {
            stageRenderer = ShapeRenderer()
            worldRenderer = Box2DDebugRenderer()

            mainStage.isDebugAll = true
        } else {
            initBackGround()
            grassDrawer = GrassDrawer(mainStage)
        }

        val manager: AssetManager = context.inject()

        stageRenderer = ShapeRenderer()
        levelBuilder = LevelBuilder(world, manager)
        Gdx.input.inputProcessor = InputMultiplexer(uiStage, KeyGestureDetector(this::processJump, GestureController(this::processJump)))

        drawLevel()
        fadeIn(0.3f)
    }

    private fun fadeIn(duration: Float) {
        backgroundStage.root.color.a = 0f
        mainStage.root.color.a = 0f
        uiStage.root.color.a = 0f

        backgroundStage.addAction(Actions.fadeIn(duration))
        mainStage.addAction(Actions.fadeIn(duration))
        uiStage.addAction(Actions.fadeIn(duration))
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

        levelBuilder.build("level4.xml", mainStage)

        player = levelBuilder.playerActor
        zoomManager.zooms = levelBuilder.zooms

        gameState = GameState.run
    }

    private fun restart() {
        gameState = GameState.restart
        levelBuilder.clearLevel()
        zoomManager.restart()

        drawLevel()
    }

    private fun processJump() {
        if (gameState == GameState.gameover) {
            restart()
            return
        }

        if (levelBuilder.onPlatform(player)) {
            player.jumpSmall()
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

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)

        if (desktopMode) {
            leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT)
            rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        } else {
            leftPressed = uiStage.leftPressed
            rightPressed = uiStage.rightPressed
        }

        if (player.y < 2.0f) {
            gameState = GameState.gameover
        }

        if (gameState == GameState.run) {
            mainStage.camera.position.set(player.x + 4, player.y, 0f)
        }

        if (!uiStage.pause) {
            world.step(1.0f / 60.0f, 6, 2)

            backgroundStage.act(delta)
            mainStage.act(delta)
            uiStage.act(delta)
        }

        processMove()
        zoomManager.updateZoom(player)
        (mainStage.camera as OrthographicCamera).zoom = zoomManager.zoomValue

        if (debugMode) {
//            mainStage.draw()
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

//        frameRate.update()
//        frameRate.render()
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