package ru.alexkulikov.firstfame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import ktx.inject.Context
import ru.alexkulikov.firstfame.screen.GameScreen
import ru.alexkulikov.firstfame.screen.MainMenu
import ru.alexkulikov.firstfame.screen.ScreenType

class App : Game() {

    private val context = Context()
    private lateinit var currentScreen: Screen

    override fun create() {
        context.register {
            bindSingleton(AssetManager())
            bindSingleton(this@App)
        }

        currentScreen = MainMenu(context)

        TextureLoader.load()
        setScreen(currentScreen)
    }

    fun switchScreen(type: ScreenType) {
        currentScreen.hide()
        currentScreen.dispose()

        currentScreen = when (type) {
            ScreenType.GAME -> GameScreen(context,false, desktopMode = true)
            ScreenType.MENU_MAIN -> MainMenu(context)
        }

        setScreen(currentScreen)
    }

    override fun dispose() {
        super.dispose()
        val manager: AssetManager = context.inject()
        manager.dispose()

        context.dispose()
    }
}