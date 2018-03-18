package ru.alexkulikov.firstfame.objects.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ru.alexkulikov.firstfame.Path

class UiButton(manager: AssetManager, type: ButtonType, uiStage: Stage, onTouchUp: () -> Unit, onTouchDown: () -> Unit) {

    private val button: ImageButton

    init {
        val skin = Skin(manager.get<TextureAtlas>(Path.buttonsAtlas))
        val style = ImageButton.ImageButtonStyle()

        when (type) {
            ButtonType.LEFT -> {
                style.imageUp = skin.getDrawable("left")
                style.imageDown = skin.getDrawable("left_pressed")

                button = ImageButton(style)
                button.setBounds(0f,0f,200f,200f)
            }
            ButtonType.RIGHT -> {
                style.imageUp = skin.getDrawable("right")
                style.imageDown = skin.getDrawable("right_pressed")

                button = ImageButton(style)
                button.setBounds(200f,0f,200f,200f)
            }
            ButtonType.PAUSE -> {
                style.imageUp = skin.getDrawable("pause")
                style.imageDown = skin.getDrawable("pause_pressed")

                button = ImageButton(style)
                button.setBounds(Gdx.graphics.width - 50f, Gdx.graphics.height - 50f,50f,50f)
            }
        }

        uiStage.addActor(button)

        button.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) =
                    onTouchUp()

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onTouchDown()
                return true
            }
        })
    }
}