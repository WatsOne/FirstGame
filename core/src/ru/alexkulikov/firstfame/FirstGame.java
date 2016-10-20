package ru.alexkulikov.firstfame;

import com.badlogic.gdx.Game;

public class FirstGame extends Game {
	
	@Override
	public void create () {
		TextureLoader.load();
		setScreen(new MainScreen());
	}
}
