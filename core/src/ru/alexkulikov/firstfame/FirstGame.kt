package ru.alexkulikov.firstfame

import com.badlogic.gdx.Game

class FirstGame : Game() {

    override fun create() {
        TextureLoader.load()
        setScreen(MainScreen())
    }
}
