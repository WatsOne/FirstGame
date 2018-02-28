package ru.alexkulikov.firstfame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.alexkulikov.firstfame.GestureController
import ru.alexkulikov.firstfame.KeyGestureDetector
import ru.alexkulikov.firstfame.objects.Constants

class MainMenu(private val switchScreen: (type: ScreenType) -> Unit) : Screen {

    private lateinit var mainStage: Stage

    override fun show() {
        mainStage = Stage(FitViewport(Constants.VIEWPORT_WIDTH.toFloat(), Constants.VIEWPORT_HEIGHT))
        Gdx.input.inputProcessor = KeyGestureDetector({switchScreen(ScreenType.GAME)}, GestureController({switchScreen(ScreenType.GAME)}))
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)
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