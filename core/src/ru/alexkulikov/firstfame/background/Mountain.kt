package ru.alexkulikov.firstfame.background

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

import ru.alexkulikov.firstfame.FadeActor
import ru.alexkulikov.firstfame.TextureLoader

import ru.alexkulikov.firstfame.objects.Constants.*

class Mountain : FadeActor() {
    private val spriteWidth = VIEWPORT_WIDTH / RATIO_STANDARD
    val sprite: Sprite = Sprite(TextureLoader.getMountain())

    val spriteOffset: Float
        get() = sprite.x + sprite.width

    init {
        sprite.setBounds(0f, 0f, spriteWidth, VIEWPORT_WIDTH.toFloat())
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch.draw(sprite, sprite.x, 0.0f, spriteWidth, VIEWPORT_WIDTH.toFloat())
    }

    fun setSpriteX(x: Float) {
        sprite.setBounds(x, 0f, spriteWidth, VIEWPORT_WIDTH.toFloat())
    }
}
