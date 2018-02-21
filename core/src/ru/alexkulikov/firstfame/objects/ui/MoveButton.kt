package ru.alexkulikov.firstfame.objects.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class MoveButton(skin: Skin, type: ButtonType, uiStage: Stage, onTouchUp: () -> Unit,
                 onTouchDown: () -> Unit) : ImageButton(skin) {
    init {
        when (type) {
            ButtonType.LEFT -> setBounds(0f,0f,200f,200f)
            ButtonType.RIGHT -> setBounds(200f,0f,200f,200f)
        }

        uiStage.addActor(this)

        addListener(object : InputListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) =
                    onTouchUp()

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onTouchDown()
                return true
            }
        })
    }
}