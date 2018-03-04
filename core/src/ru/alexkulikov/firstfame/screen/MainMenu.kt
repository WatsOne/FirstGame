package ru.alexkulikov.firstfame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.alexkulikov.firstfame.App
import ru.alexkulikov.firstfame.GestureController
import ru.alexkulikov.firstfame.KeyGestureDetector
import ru.alexkulikov.firstfame.objects.Constants
import ktx.inject.Context

class MainMenu(private val context: Context) : Screen {

    private lateinit var mainStage: Stage

    private var canPlay = false
    private val manager: AssetManager = context.inject()

    override fun show() {
        loadAssets()

        mainStage = Stage(FitViewport(Constants.VIEWPORT_WIDTH.toFloat(), Constants.VIEWPORT_HEIGHT))
        Gdx.input.inputProcessor = KeyGestureDetector(this::switchScreen, GestureController(this::switchScreen))

        val pixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGB888)
        pixmap.setColor(Color.GREEN)
        pixmap.fill()
        val texture = Texture(pixmap)
        pixmap.dispose()
        mainStage.addActor(Image(texture))

        mainStage.root.color.a = 0f
        mainStage.addAction(Actions.fadeIn(0.3f))
    }

    private fun switchScreen() {
        if (!canPlay) return

        val actions = SequenceAction()
        actions.addAction(Actions.fadeOut(0.3f))
        actions.addAction(Actions.run( { context.inject<App>().switchScreen(ScreenType.GAME) } ))
        mainStage.addAction(actions)
    }

    private fun loadAssets() {
        val manager: AssetManager = context.inject()
        manager.load("object/coin.atlas", TextureAtlas::class.java)
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        canPlay = manager.update()

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)
        mainStage.act(delta)
        mainStage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        mainStage.dispose()
    }

}