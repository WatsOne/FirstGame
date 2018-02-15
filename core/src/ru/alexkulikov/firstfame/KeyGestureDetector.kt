package ru.alexkulikov.firstfame

import com.badlogic.gdx.Input
import com.badlogic.gdx.input.GestureDetector

class KeyGestureDetector(private val callback: KeysCallback, gestureController: GestureController) : GestureDetector(gestureController) {
    override fun keyDown(keycode: Int): Boolean {
        if (Input.Keys.SPACE == keycode) {
            callback.onJump()
        }
        return true
    }
}