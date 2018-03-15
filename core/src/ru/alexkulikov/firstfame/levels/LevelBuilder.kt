package ru.alexkulikov.firstfame.levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.XmlReader
import ru.alexkulikov.firstfame.Path
import ru.alexkulikov.firstfame.objects.*
import ru.alexkulikov.firstfame.objects.player.Player
import ru.alexkulikov.firstfame.objects.player.QuadPlayer

class LevelBuilder(private val world: World, private val manager: AssetManager) {
    private val reader = XmlReader()
    private var contactPlatforms = mutableListOf<Polygon>()

    private lateinit var levelGroup: Group
    private lateinit var playerActor: Player
    private lateinit var exitActor: Exit

    private val woodTexture: Texture = manager.get(Path.woodMaterial)
    private val iceTexture: Texture = manager.get(Path.iceMaterial)

    init {
        woodTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        iceTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }

    fun build(levelName: String, stage: Stage, callback: (Player) -> (Unit)) {
        contactPlatforms.clear()
        levelGroup = Group()

        val root = reader.parse(Gdx.files.internal("levels/" + levelName))

        buildBoxes(root)
        stage.addActor(levelGroup)

        val exit = root.getChildByName("exit")
        val exitPos = parseCoordinates(exit)
        exitActor = Exit(manager, exitPos.first, exitPos.second)
        stage.addActor(exitActor)

        val player = root.getChildByName("player")
        val playerPos = parseCoordinates(player)
        playerActor = QuadPlayer(world, playerPos.first, playerPos.second, 0.4f, 0.4f)
        stage.addActor(playerActor)

        callback(playerActor)
    }

    private fun parseCoordinates(element: XmlReader.Element): Pair<Float, Float> {
        return Pair(element.getFloatAttribute("x"), element.getFloatAttribute("y"))
    }

    private fun buildBoxes(root: XmlReader.Element) {
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

            val materialTexture = when (material) {
                Material.wood -> woodTexture
                Material.ice -> iceTexture
                else -> {
                    woodTexture
                }
            }

            when (type) {
                "box" -> {
                    val platform = Platform(materialTexture, world, material, x, y, w, h)
                    contactPlatforms.add(platform.contactPolygon)
                    levelGroup.addActor(platform)
                }
                "ground" -> {
                    val platform = Platform(materialTexture, world, material, x, y, w, h, BodyDef.BodyType.StaticBody)
                    contactPlatforms.add(platform.contactPolygon)
                    levelGroup.addActor(platform)
                }
                "seesaw" -> {
                    val platform = Platform(materialTexture, world, ObjectType.SEESAW, material, x, y, w, h)
                    contactPlatforms.add(platform.contactPolygon)
                    levelGroup.addActor(platform)
                }
                "circle" -> levelGroup.addActor(CircleBox(world, material, x, y, h))
            }
        }
    }

    private fun clearWorld() {
        val bodies = Array<Body>()
        val joints = Array<Joint>()
        world.getBodies(bodies)
        world.getJoints(joints)
        joints.forEach { world.destroyJoint(it) }
        bodies.forEach {
            val data = it.userData as BoxData?
            if (data == null || data.type != ObjectType.GROUND) {
                world.destroyBody(it)
            }
        }
    }

    fun clearLevel() {
        levelGroup.remove()
        exitActor.remove()
        playerActor.remove()
        clearWorld()
    }

    fun onPlatform(player: Player): Boolean =
            contactPlatforms
                    .filter { it.x > player.x - 4 && it.x < player.x + 4}
                    .any { player.overlaps(it) }
}