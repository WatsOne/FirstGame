package ru.alexkulikov.firstfame

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import ru.alexkulikov.firstfame.objects.ui.ButtonType
import ru.alexkulikov.firstfame.objects.ui.UiButton

class UiStage(viewPort: Viewport, manager: AssetManager, debug: Boolean, desktop: Boolean) : Stage(viewPort) {
    var leftPressed = false
    var rightPressed = false
    var pause = false

    init {
        if (!debug) {

            addActor(UiButton(manager, ButtonType.PAUSE, {}, {pause = !pause}).button)

            if (!desktop) {
                addActor(UiButton(manager, ButtonType.LEFT, {leftPressed = false}, {leftPressed = true}).button)
                addActor(UiButton(manager, ButtonType.RIGHT, {rightPressed = false}, {rightPressed = true}).button)
            }
        }
    }
}