package com.nacs.lootgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.nacs.lootgame.game.Assets;
import com.nacs.lootgame.screens.MainMenu;

public class LootGameMain extends Game {
	
	@Override
	public void create() {
		// set libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// load assets
		Assets.instance.init(new AssetManager());
		// start game at menu screen
		setScreen(new MainMenu(this));
	}
	
}
