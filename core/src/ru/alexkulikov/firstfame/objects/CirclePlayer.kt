package ru.alexkulikov.firstfame.objects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.World

class CirclePlayer(world: World, private val h: Float) : GameObject(world) {
    public val contactShape = Circle(0f, 0f, h/2)

    public fun createBody() {
        val shape = CircleShape()
        shape.radius = h/2
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, Material.wood)
        setOrigin(h/2, h/2)
        color = Color.BLUE
    }

    override fun act(delta: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        rotation = MathUtils.radiansToDegrees * body.angle

        contactShape.setPosition(x, y)
    }

    fun jump(power: Float) =
            body.applyLinearImpulse(Vector2(power * 0.8f, power * 2.5f), body.position, true)

    fun getLinearVelocity(): Vector2 = body.linearVelocity
}