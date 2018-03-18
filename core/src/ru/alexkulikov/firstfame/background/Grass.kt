package ru.alexkulikov.firstfame.background

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor


import ru.alexkulikov.firstfame.TextureLoader

import ru.alexkulikov.firstfame.objects.Constants.*

class Grass : Actor() {
    val sprite: Sprite = Sprite(TextureLoader.getGrass())
    private val scalableY: Float = VIEWPORT_WIDTH / RATIO_STANDARD

    val spriteOffset: Float
        get() = sprite.x + sprite.width

    init {
        sprite.setBounds(0f, 0f, scalableY, VIEWPORT_WIDTH.toFloat())
    }

    override fun draw(batch: Batch, parentAlpha: Float) =
            batch.draw(sprite, sprite.x, -scalableY / 2.0f, scalableY, VIEWPORT_WIDTH.toFloat())

    fun setSpriteX(x: Float) = sprite.setBounds(x, -3.6f, scalableY, VIEWPORT_WIDTH.toFloat())
}
