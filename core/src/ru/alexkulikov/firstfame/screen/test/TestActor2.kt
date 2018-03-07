package ru.alexkulikov.firstfame.screen.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

class TestActor2(texture: Texture) : Actor() {
    private val sprite: Sprite = Sprite(texture)

    init {
        sprite.setBounds(2f, 2f, 1f, 1f)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        sprite.draw(batch)
    }
}