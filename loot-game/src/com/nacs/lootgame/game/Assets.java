package com.nacs.lootgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.nacs.lootgame.utils.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Assets implements Disposable, AssetErrorListener{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	
	public AssetSword swords;
	public AssetMiscObjects misc;
	public AssetFonts fonts;
	public AssetPlayerCharacter pc;
	public AssetEnemies enemies;
		
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_SWORDS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_UI, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_PCS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_ENEMIES, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) Gdx.app.debug(TAG, "asset: " + a);
		
		TextureAtlas swordsAtlas = assetManager.get(Constants.TEXTURE_ATLAS_SWORDS);
		TextureAtlas miscObjectAtlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		TextureAtlas pcAtlas = assetManager.get(Constants.TEXTURE_ATLAS_PCS);
		TextureAtlas enemiesAtlas = assetManager.get(Constants.TEXTURE_ATLAS_ENEMIES);
		
		//create game resource objects
		fonts = new AssetFonts();
		swords = new AssetSword(swordsAtlas);
		misc = new AssetMiscObjects(miscObjectAtlas);
		pc = new AssetPlayerCharacter(pcAtlas);
		enemies = new AssetEnemies(enemiesAtlas);
		
	}
	
	public class AssetSword {
		public final AtlasRegion sword00;
		public final AtlasRegion sword01;
		public AssetSword (TextureAtlas atlas) {
			sword00 = atlas.findRegion("sword00");
			sword01 = atlas.findRegion("sword01");
		}
	}
	
	public class AssetPlayerCharacter {
		public final AtlasRegion pc00;
		public AssetPlayerCharacter (TextureAtlas atlas) {
			pc00 = atlas.findRegion("pc00");
		}
	}
	
	public class AssetEnemies {
		public final AtlasRegion enemy00;
		public AssetEnemies (TextureAtlas atlas) {
			enemy00 = atlas.findRegion("enemy00");
		}
	}
	
	public class AssetMiscObjects {
		public final AtlasRegion itemWindowBackground;
		public AssetMiscObjects (TextureAtlas atlas) {
			itemWindowBackground = atlas.findRegion("item-window-background");
		}
	}
	
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts() {
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/default.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/default.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/default.fnt"), true);
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			//enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}
	
	//@Override
	public void error(String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '"+filename+"'", (Exception)throwable);
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '"+asset.fileName+"'", (Exception)throwable);
	}
}
