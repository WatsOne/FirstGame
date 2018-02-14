package ru.alexkulikov.firstfame.objects.player

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ru.alexkulikov.firstfame.objects.GameObject

open class Player(world: World) : GameObject(world) {
    private val forceVector = Vector2(0f,0f)
    private val jumpVector = Vector2(0f,0f)

    open fun clearManual() {
        remove()
    }

    fun jump(power: Float) =
            body.applyLinearImpulse(Vector2(power * 0.8f, power * 2.5f), body.position, true)

    fun moveLeft() = body.applyForceToCenter(forceVector.set(-1.0f, 0.0f), true)
    fun moveRight() = body.applyForceToCenter(forceVector.set(1.0f, 0.0f), true)
    fun jumpSmall() = body.applyLinearImpulse(jumpVector.set(0.0f, 0.5f), body.position, true)

    val linearVelocity: Vector2
        get() = body.linearVelocity

    open fun overlaps(polygon: Polygon): Boolean = false
}