package ru.alexkulikov.firstfame

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import ru.alexkulikov.firstfame.objects.BoxData
import ru.alexkulikov.firstfame.objects.ObjectType

class WorldContactListener(private val canJumpCallback: (Boolean) -> Unit, private val gameOverCallback: () -> Unit) : ContactListener {

    override fun beginContact(contact: Contact?) = Unit
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) = Unit
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) = Unit

    override fun endContact(contact: Contact) {
        val boxDataA = contact.fixtureA.body.userData as BoxData?
        val boxDataB = contact.fixtureB.body.userData as BoxData?

        val canJump = boxDataA != null && boxDataA.type == ObjectType.player && boxDataB != null && boxDataB.type == ObjectType.box
        canJumpCallback(canJump)

        val gameOver = boxDataA != null && boxDataA.type == ObjectType.ground && boxDataB != null && boxDataB.type == ObjectType.player
        if (gameOver) gameOverCallback()
    }
}
