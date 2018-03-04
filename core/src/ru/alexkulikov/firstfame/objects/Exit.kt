package ru.alexkulikov.firstfame.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.alexkulikov.firstfame.FadeActor
import ru.alexkulikov.firstfame.Path

class Exit(manager: AssetManager) : FadeActor() {

    private val atlas = manager.get(Path.coinAtlas, TextureAtlas::class.java)
    private var animation = Animation(1/9f, atlas.findRegions("coin"), Animation.PlayMode.LOOP)
    private var delta = 0f

    init {
        setBounds(5f, 5f, 1f, 1f)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        delta += Gdx.graphics.deltaTime
        val keyFrame = animation.getKeyFrame(delta, true)
        batch.draw(keyFrame, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}