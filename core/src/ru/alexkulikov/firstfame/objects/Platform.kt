package ru.alexkulikov.firstfame.objects

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World

class Platform : Box {

    lateinit var contactPolygon: Polygon

    constructor(world: World, material: Material, x: Float, y: Float, h: Float, w: Float) : super(world, material, x, y, h, w) {
        createContactPolygon(h, w)
    }

    constructor(world: World, material: Material, x: Float, y: Float, h: Float, w: Float, type: BodyDef.BodyType) : super(world, material, x, y, h, w, type) {
        createContactPolygon(h, w)
    }

    constructor(world: World, oType: ObjectType, material: Material, x: Float, y: Float, h: Float, w: Float) : super(world, oType, material, x, y, h, w) {
        createContactPolygon(h, w)
    }

    private fun createContactPolygon(h: Float, w: Float) {
        contactPolygon = Polygon(floatArrayOf(0 - 0.1f, 0 - 0.1f, h + 0.1f, 0 - 0.1f, h + 0.1f, w + 0.1f, 0 - 0.1f, w + 0.1f))
        contactPolygon.setOrigin(h / 2, w / 2)
    }

    override fun act(delta: Float) {
        contactPolygon.setPosition(x, y)
        contactPolygon.rotation = MathUtils.radiansToDegrees * body.angle
        super.act(delta)
    }
}
