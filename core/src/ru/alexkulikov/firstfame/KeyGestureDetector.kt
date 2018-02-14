package ru.alexkulikov.firstfame

import com.badlogic.gdx.Input
import com.badlogic.gdx.input.GestureDetector
import ru.alexkulikov.firstfame.objects.player.Player

class KeyGestureDetector(val player: Player, gestureController: GestureController) : GestureDetector(gestureController) {
    override fun keyDown(keycode: Int): Boolean {
        if (Input.Keys.SPACE == keycode) {
            player.jumpSmall()
        }
        return true
    }
}