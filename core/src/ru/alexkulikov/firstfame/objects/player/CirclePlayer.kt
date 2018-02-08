package ru.alexkulikov.firstfame.objects.player

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.World
import ru.alexkulikov.firstfame.PolygonUtils
import ru.alexkulikov.firstfame.objects.Material
import ru.alexkulikov.firstfame.objects.ObjectType

class CirclePlayer(world: World, x: Float, y:Float, private val h: Float) : Player(world) {
    init {
        setBounds(x, y, h, h)
        createBody()
    }

    private val contactShape = Circle(0f, 0f, h/2)
    private val polygonUtils = PolygonUtils()

    private fun createBody() {
        val shape = CircleShape()
        shape.radius = h/2
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, Material.wood)
        setOrigin(h/2, h/2)
    }

    override fun act(delta: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        rotation = MathUtils.radiansToDegrees * body.angle

        contactShape.setPosition(x, y)
    }

    override fun overlaps(polygon: Polygon): Boolean = polygonUtils.overlaps(polygon, contactShape)
}