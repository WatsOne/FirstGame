package ru.alexkulikov.firstfame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.alexkulikov.firstfame.App
import ru.alexkulikov.firstfame.GestureController
import ru.alexkulikov.firstfame.KeyGestureDetector
import ru.alexkulikov.firstfame.objects.Constants
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MainMenu : Screen {

    private lateinit var mainStage: Stage
    private lateinit var coin: Animation<TextureRegion>
    private lateinit var batch: SpriteBatch
    private var stateTime: Float = 0f

    override fun show() {
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

        val atlas = TextureAtlas(Gdx.files.internal("object/coin.atlas"));
        coin = Animation(1/9f, atlas.findRegions("coin"), Animation.PlayMode.LOOP)
        Gdx.app.log("S", coin.keyFrames.size.toString())
        batch = SpriteBatch()
    }

    private fun switchScreen() {
        val actions = SequenceAction()
        actions.addAction(Actions.fadeOut(0.3f))
        actions.addAction(Actions.run( { (Gdx.app.applicationListener as App).switchScreen(ScreenType.GAME) } ))
        mainStage.addAction(actions)
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)
        mainStage.act(delta)
        mainStage.draw()

        stateTime += delta
        val currentFrame = coin.getKeyFrame(stateTime, true)
        batch.begin()
        batch.draw(currentFrame, 50f, 50f) // Draw current frame at (50, 50)
        batch.end()
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