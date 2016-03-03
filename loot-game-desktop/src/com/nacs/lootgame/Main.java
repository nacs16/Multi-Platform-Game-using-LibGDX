package com.nacs.lootgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Main {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;
	
	public static void main(String[] args) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images", "../loot-game-android/assets/images/texturepacks", "lootgame.pack");
			TexturePacker.process(settings, "assets-raw/images-ui", "../loot-game-android/assets/images/texturepacks", "lootgame-ui.pack");
			TexturePacker.process(settings, "assets-raw/swords", "../loot-game-android/assets/images/texturepacks", "lootgame-swords.pack");
			TexturePacker.process(settings, "assets-raw/player-characters", "../loot-game-android/assets/images/texturepacks", "lootgame-pcs.pack");
			TexturePacker.process(settings, "assets-raw/enemies", "../loot-game-android/assets/images/texturepacks", "lootgame-enemeis.pack");
		}
		
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "loot-game";
		cfg.width = 1024;
		cfg.height = 512;
		
		new LwjglApplication(new LootGameMain(), cfg);
	}
}
