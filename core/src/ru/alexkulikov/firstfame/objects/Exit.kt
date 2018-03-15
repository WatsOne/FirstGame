package ru.alexkulikov.firstfame.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.alexkulikov.firstfame.FadeActor
import ru.alexkulikov.firstfame.Path

class Exit(manager: AssetManager, x: Float, y: Float) : FadeActor() {

    private val atlas = manager.get(Path.coinAtlas, TextureAtlas::class.java)
    private var animation = Animation(1/12f, atlas.findRegions("coin"), Animation.PlayMode.LOOP)
    private var delta = 0f

    init {
        val w = 0.4f
        val h = 0.4f

        setBounds(x, y, w, h)
        setOrigin(w / 2, h / 2)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        delta += Gdx.graphics.deltaTime
        val keyFrame = animation.getKeyFrame(delta, true)
        batch.draw(keyFrame, x - width / 2, y - height / 2, width, height)
    }
}