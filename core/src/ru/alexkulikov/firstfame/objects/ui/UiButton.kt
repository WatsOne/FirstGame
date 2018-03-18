package ru.alexkulikov.firstfame.objects.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class UiButton(x: Float, y: Float, skin: Skin, uiStage: Stage, onTouchDown: () -> Unit) : ImageButton(skin) {
    init {
        setBounds(x, y,50f,50f)

        uiStage.addActor(this)

        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onTouchDown()
                return true
            }
        })
    }
}