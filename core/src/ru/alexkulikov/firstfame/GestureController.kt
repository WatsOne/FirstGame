package ru.alexkulikov.firstfame

import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2

class GestureController(private val callback: GestureCallback) : GestureDetector.GestureListener {
    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean = false
    override fun zoom(initialDistance: Float, distance: Float): Boolean = false
    override fun pinchStop() = Unit
    override fun longPress(x: Float, y: Float): Boolean = false
    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean = false
    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean = false

    var mainDeltaX = 0f
    private val maxPowerLength = 200f

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        callback.tap()
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        mainDeltaX += deltaX
        callback.onTap(getPower())
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        callback.jump(getPower())
        mainDeltaX = 0f
        return true
    }

    private fun getPower() = 0.2f + Math.max(Math.min(mainDeltaX, maxPowerLength), 0f) / 1000f
}