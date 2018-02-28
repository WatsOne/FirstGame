package ru.alexkulikov.firstfame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import ru.alexkulikov.firstfame.screen.GameScreen
import ru.alexkulikov.firstfame.screen.MainMenu
import ru.alexkulikov.firstfame.screen.ScreenType

class App : Game() {

    private var currentScreen: Screen = MainMenu(this::switchScreen)

    override fun create() {
        TextureLoader.load()
        setScreen(currentScreen)
    }

    private fun switchScreen(type: ScreenType) {
        currentScreen.hide()
        currentScreen.dispose()

        currentScreen = when (type) {
            ScreenType.GAME -> {
                GameScreen(false, desktopMode = true)
            }
            ScreenType.MENU_MAIN -> {
                MainMenu(this::switchScreen)
            }
        }

        setScreen(currentScreen)
    }
}