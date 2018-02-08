package ru.alexkulikov.firstfame.objects.player

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World

import ru.alexkulikov.firstfame.TailDrawer
import ru.alexkulikov.firstfame.TextureLoader
import ru.alexkulikov.firstfame.objects.Material
import ru.alexkulikov.firstfame.objects.ObjectType

class QuadPlayer(world: World, x: Float, y:Float, private val h: Float, private val w: Float) : Player(world) {

    private val contactShape = Polygon(floatArrayOf(0f, 0f, w, 0f, w, h, 0f, h))
    private val sprite =  Sprite(TextureLoader.getPlayer())
    private val tailDrawer = TailDrawer(h, w)

    init {
        setBounds(x, y, w, h)
        createBody()
    }

    private fun createBody() {
        val shape = PolygonShape()
        shape.setAsBox(w / 2, h / 2)
        val material = Material.wood
        createBody(shape, ObjectType.player, BodyDef.BodyType.DynamicBody, material.restitution, material.density, material.friction)
        setOrigin(w / 2, h / 2)

        contactShape.setOrigin(w / 2, h / 2)
        sprite.setBounds(3f, 1.5f, 0.4f, 0.4f)
        sprite.setOriginCenter()
    }

    override fun act(delta: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        rotation = MathUtils.radiansToDegrees * body.angle

        contactShape.setPosition(x, y)
        contactShape.rotation = MathUtils.radiansToDegrees * body.angle

        sprite.setPosition(body.position.x - width / 2, body.position.y - height / 2)
        sprite.rotation = MathUtils.radiansToDegrees * body.angle

        tailDrawer.update(this)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.end()
        tailDrawer.draw(stage.camera)
        batch.begin()
        sprite.draw(batch)
    }

    override fun clearManual() {
        tailDrawer.clear()
        tailDrawer.dispose()
        super.clearManual()
    }

    override fun overlaps(polygon: Polygon): Boolean =
            Intersector.overlapConvexPolygons(polygon, contactShape)
}
