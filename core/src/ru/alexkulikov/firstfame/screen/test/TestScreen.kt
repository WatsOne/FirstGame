package ru.alexkulikov.firstfame.screen.test

import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.EarClippingTriangulator
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.inject.Context
import ru.alexkulikov.firstfame.Path
import ru.alexkulikov.firstfame.objects.Constants


class TestScreen(private val context: Context) : Screen {

    private lateinit var stage: Stage
    private lateinit var stage2: Stage
    private lateinit var polygonRegion: PolygonRegion
    private val a = 100f
    private val b = 100f

    private lateinit var sBatch: SpriteBatch
    private lateinit var sCamera: OrthographicCamera
    private lateinit var sSprite: Sprite
    private lateinit var sSprite2: Sprite

    private lateinit var pBatch: PolygonSpriteBatch
    private lateinit var pSprite: PolygonSprite

    private lateinit var rSprite: RepeatablePolygonSprite

    override fun hide() {
    }

    override fun show() {
//        stage = Stage(ScreenViewport(), PolygonSpriteBatch())
        stage = Stage(FitViewport(Constants.VIEWPORT_WIDTH.toFloat(), Constants.VIEWPORT_HEIGHT), PolygonSpriteBatch())
        stage2 = Stage(FitViewport(Constants.VIEWPORT_WIDTH.toFloat(), Constants.VIEWPORT_HEIGHT))

        val manager: AssetManager = context.inject()
        val texture: Texture = manager.get(Path.woodMaterial)
        val texture2: Texture = manager.get(Path.woodMaterial)
//        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        val vertices = floatArrayOf(200f, 100f,200f,200f,1000f,200f,1000f,100f)
        val triangleIndices = EarClippingTriangulator().computeTriangles(vertices).toArray()

        val polygonRegion = PolygonRegion(TextureRegion(texture), vertices, triangleIndices)

        val polyActor = TestActor(polygonRegion)
//        polyActor.setPosition(1f, 1f)
        stage.addActor(polyActor)
        stage2.addActor(TestActor2(texture))

        sCamera = OrthographicCamera()
        sBatch = SpriteBatch()
        sCamera.setToOrtho(false, Constants.VIEWPORT_WIDTH.toFloat(), Constants.VIEWPORT_HEIGHT)
        sBatch.projectionMatrix = sCamera.combined

        texture2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        sSprite = Sprite(TextureRegion(texture2))

        val w = 5f
        val h = 2f

        sSprite.u = -(w/h - 1)

        sSprite2 = Sprite(texture)
        sSprite2.setBounds(1f,2f,1f,1f)

        sSprite.setBounds(1f,1f,w,h)


        pSprite = PolygonSprite(polygonRegion)
//        pSprite.setBounds(1f,1f,4f,1f)
//        pSprite.setOrigin(1f,1f)

        pBatch = PolygonSpriteBatch()
        pBatch.projectionMatrix = sCamera.combined

//        rSprite = RepeatablePolygonSprite()
//        rSprite.setPolygon(TextureRegion(texture), vertices)
//        rSprite.setPosition(1f,1f)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT )

        sBatch.begin()
        sSprite.draw(sBatch)
//        sSprite2.draw(sBatch)
        sBatch.end()

//        pBatch.begin()
//        pSprite.draw(pBatch)
//        rSprite.draw(pBatch)
//        pBatch.end()

//        stage.act()
//        stage2.act()
//        stage.draw()
//        stage2.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        stage.dispose()
        stage2.dispose()
        sBatch.dispose()
    }
}
