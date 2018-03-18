package ru.alexkulikov.firstfame.background

import com.badlogic.gdx.scenes.scene2d.Stage

class MountainDrawer(stage: Stage) {
    private var leftMountain = Mountain()
    private var centralMountain = Mountain()
    private var rightMountain = Mountain()

    private var mainOffset = 0f

    init {
        stage.addActor(leftMountain)
        stage.addActor(centralMountain)
        stage.addActor(rightMountain)
    }

    fun initialize() {
        centralMountain.setSpriteX(0f)
        leftMountain.setSpriteX(centralMountain.sprite.x - leftMountain.sprite.width)
        rightMountain.setSpriteX(centralMountain.spriteOffset)
        mainOffset = 0f
    }

    fun update(camX: Float) {
        centralMountain.setSpriteX(0 - camX / 2 + mainOffset)
        leftMountain.setSpriteX(centralMountain.sprite.x - leftMountain.sprite.width)
        rightMountain.setSpriteX(centralMountain.spriteOffset)

        if (leftMountain.spriteOffset < -leftMountain.sprite.width / 2) {
            changeLeft()
        } else if (rightMountain.sprite.x > rightMountain.sprite.width * 1.5) {
            changeRight()
        }
    }

    private fun changeLeft() {
        val buffer = leftMountain
        mainOffset += leftMountain.sprite.width

        leftMountain = centralMountain
        centralMountain = rightMountain
        rightMountain = buffer

        rightMountain.setSpriteX(centralMountain.spriteOffset)
    }

    private fun changeRight() {
        val buffer = rightMountain
        mainOffset -= rightMountain.sprite.width

        rightMountain = centralMountain
        centralMountain = leftMountain
        leftMountain = buffer

        leftMountain.setSpriteX(centralMountain.spriteOffset)
    }
}
