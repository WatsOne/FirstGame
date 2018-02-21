package ru.alexkulikov.firstfame

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import ru.alexkulikov.firstfame.objects.BoxData
import ru.alexkulikov.firstfame.objects.ObjectType

class WorldContactListener(private val gameOverCallback: () -> Unit) : ContactListener {

    private var collisions = 0

    override fun beginContact(contact: Contact) {
        val boxDataA = contact.fixtureA.body.userData as BoxData?
        val boxDataB = contact.fixtureB.body.userData as BoxData?

        val canJump = boxDataA != null && boxDataA.type == ObjectType.PLAYER && boxDataB != null && boxDataB.type == ObjectType.BOX ||
                      boxDataA != null && boxDataA.type == ObjectType.BOX && boxDataB != null && boxDataB.type == ObjectType.PLAYER

        if (canJump) {
            collisions++
        }

        val gameOver = boxDataA != null && boxDataA.type == ObjectType.GROUND && boxDataB != null && boxDataB.type == ObjectType.PLAYER ||
                       boxDataA != null && boxDataA.type == ObjectType.PLAYER && boxDataB != null && boxDataB.type == ObjectType.GROUND

        if (gameOver) gameOverCallback()
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold?) = Unit
    override fun postSolve(contact: Contact, impulse: ContactImpulse?) = Unit

    override fun endContact(contact: Contact) {
        val boxDataA = contact.fixtureA.body.userData as BoxData?
        val boxDataB = contact.fixtureB.body.userData as BoxData?

        val canJump = boxDataA != null && boxDataA.type == ObjectType.PLAYER && boxDataB != null && boxDataB.type == ObjectType.BOX ||
                boxDataA != null && boxDataA.type == ObjectType.BOX && boxDataB != null && boxDataB.type == ObjectType.PLAYER

        if (canJump) {
            collisions--
        }
    }

    fun canJump() = collisions > 0
}
