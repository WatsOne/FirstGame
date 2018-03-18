package ru.alexkulikov.firstfame.background

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage

import ru.alexkulikov.firstfame.objects.Constants

class GrassDrawer(stage: Stage) {
    private var leftGrass = Grass()
    private var centralGrass = Grass()
    private var rightGrass = Grass()

    init {
        stage.addActor(leftGrass)
        stage.addActor(centralGrass)
        stage.addActor(rightGrass)
    }

    fun initialize() {
        centralGrass.setSpriteX(0f)
        leftGrass.setSpriteX(-leftGrass.sprite.width)
        rightGrass.setSpriteX(centralGrass.spriteOffset)
    }

    fun update(camX: Float) {
        if (camX - 3 > leftGrass.spriteOffset) {
            changeRight()
        } else if (camX + Constants.VIEWPORT_WIDTH.toFloat() + 3f < rightGrass.sprite.x) {
            changeLeft()
        }
    }

    private fun changeRight() {
        val buffer = leftGrass

        leftGrass = centralGrass
        centralGrass = rightGrass
        rightGrass = buffer

        rightGrass.setSpriteX(centralGrass.spriteOffset)
    }

    private fun changeLeft() {
        val buffer = rightGrass

        rightGrass = centralGrass
        centralGrass = leftGrass
        leftGrass = buffer

        leftGrass.setSpriteX(centralGrass.sprite.x - centralGrass.sprite.width)
    }
}
