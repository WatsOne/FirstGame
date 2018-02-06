package ru.alexkulikov.firstfame.background

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

import ru.alexkulikov.firstfame.TextureLoader

import ru.alexkulikov.firstfame.objects.Constants.*

class Grass : Actor() {
    private val sprite: Sprite
    private val scalableY: Float

    val spriteOffset: Float
        get() = sprite.x + sprite.width

    init {
        setBounds(0f, 0f, 0f, 0f)
        sprite = Sprite(TextureLoader.getGrass())
        scalableY = VIEWPORT_WIDTH / RATIO_STANDARD
        sprite.setBounds(0f, 0f, 0f, 0f)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) =
            batch!!.draw(sprite, sprite.x, -3.6f, scalableY, VIEWPORT_WIDTH.toFloat())

    fun setSpriteX(x: Float) = sprite.setBounds(x, -3.6f, scalableY, VIEWPORT_WIDTH.toFloat())
}
