package ru.alexkulikov.firstfame.levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.XmlReader
import ru.alexkulikov.firstfame.objects.*
import ru.alexkulikov.firstfame.objects.player.Player

class LevelBuilder(private val world: World) {
    private val reader = XmlReader()
    private var contactPlatforms = mutableListOf<Polygon>()
    lateinit var levelGroup: Group

    fun build(levelName: String, callback: (Float,Float) -> Unit) {
        contactPlatforms.clear()
        levelGroup = Group()

        val root = reader.parse(Gdx.files.internal("levels/" + levelName))
        val player = root.getChildByName("player")

        val playerX = java.lang.Float.parseFloat(player.getAttribute("x"))
        val playerY = java.lang.Float.parseFloat(player.getAttribute("y"))

        val boxes = root.getChildByName("boxes")
        val boxCount = boxes.childCount

        (0 until boxCount).forEach {
            val box = boxes.getChild(it)
            val type = box.name

            val x = java.lang.Float.parseFloat(box.getAttribute("x"))
            val y = java.lang.Float.parseFloat(box.getAttribute("y"))
            val w = java.lang.Float.parseFloat(box.getAttribute("w"))
            val h = java.lang.Float.parseFloat(box.getAttribute("h"))
            val material = Material.valueOf(box.getAttribute("material"))

            when (type) {
                "box" -> levelGroup.addActor(Box(world, material, x, y, w, h))
                "ground" -> levelGroup.addActor(Box(world, material, x, y, w, h, BodyDef.BodyType.StaticBody))
                "circle" -> levelGroup.addActor(CircleBox(world, material, x, y, h))
                "platform" -> {
                    val platform = Platform(world, material, x, y, w, h)
                    contactPlatforms.add(platform.contactPolygon)
                    levelGroup.addActor(platform)
                }
            }
        }

        callback(playerX, playerY)
    }

    private fun clearBoxesBodies() {
        val bodies = Array<Body>()
        world.getBodies(bodies)
        for (b in bodies) {
            val data = b.userData as BoxData?
            if (data != null && (data.type == ObjectType.box || data.type == ObjectType.player)) {
                world.destroyBody(b)
            }
        }
    }

    fun clearLevel() {
        levelGroup.remove()
        clearBoxesBodies()
    }

    fun onPlatform(player: Player): Boolean = contactPlatforms.any { player.overlaps(it) }
}