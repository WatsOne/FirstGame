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

        val canJump = boxDataA != null && boxDataA.type == ObjectType.player && boxDataB != null && boxDataB.type == ObjectType.box ||
                      boxDataA != null && boxDataA.type == ObjectType.box && boxDataB != null && boxDataB.type == ObjectType.player

        if (canJump) {
            collisions++
        }

        val gameOver = boxDataA != null && boxDataA.type == ObjectType.ground && boxDataB != null && boxDataB.type == ObjectType.player ||
                       boxDataA != null && boxDataA.type == ObjectType.player && boxDataB != null && boxDataB.type == ObjectType.ground

        if (gameOver) gameOverCallback()
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold?) = Unit
    override fun postSolve(contact: Contact, impulse: ContactImpulse?) = Unit

    override fun endContact(contact: Contact) {
        val boxDataA = contact.fixtureA.body.userData as BoxData?
        val boxDataB = contact.fixtureB.body.userData as BoxData?

        val canJump = boxDataA != null && boxDataA.type == ObjectType.player && boxDataB != null && boxDataB.type == ObjectType.box ||
                boxDataA != null && boxDataA.type == ObjectType.box && boxDataB != null && boxDataB.type == ObjectType.player

        if (canJump) {
            collisions--
        }
    }

    fun canJump() = collisions > 0
}
