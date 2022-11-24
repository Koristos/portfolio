package ru.fefelov;

import com.badlogic.gdx.Game;

import ru.fefelov.screen.impl.MenuScreen;

public class SpaceGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
