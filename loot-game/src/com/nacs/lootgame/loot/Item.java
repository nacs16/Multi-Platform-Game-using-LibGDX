package com.nacs.lootgame.loot;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.nacs.lootgame.game.Assets;
import com.nacs.lootgame.utils.Constants;

public class Item {	
	private static final boolean DEBUG = false;
	
	private String itemName;
	private TextureRegion itemTexture;
	private ItemStats itemStats;
	private Color itemRarityColor;
	
	private ItemTypes itemType;
	public static enum ItemTypes {
		SWORD;
		
		public static ItemTypes getRandomType() {
			return values()[(int)Math.random() * values().length];
		}
	}
	
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color BLUE = new Color(17.0f/255.0f, 34.0f/255.0f, 242.0f/255.0f, 1.0f);
	public static final Color PURPLE = new Color(164.0f/255.0f, 45.0f/255.0f, 208.0f/255.0f, 1.0f);
	public static final Color GREEN = new Color(24.0f/255.0f, 218.0f/255.0f, 53.0f/255.0f, 1.0f);
	public static final Color ORANGE = new Color(231.0f/255.0f, 111.0f/255.0f, 22.0f/255.0f, 1.0f);
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	public static final int SWORD_SKINS_COUNT = 2;
	
	
	public Item(int enemyScore) { //enemy score will determine the attributes of the item.
		itemType = ItemTypes.getRandomType();
		itemStats = new ItemStats();
		itemTexture = generateItemTextureRegion();
		itemName = generateItemName();
		itemRarityColor = generateItemColor();
	}
	
	private Color generateItemColor() {
		int randomColor = MathUtils.random(0, 4);
		Color color = null;
		switch (randomColor) {
		case 0:
			color = WHITE;
			break;
		case 1:
			color = GREEN;
			break;
		case 2:
			color = BLUE;
			break;
		case 3:
			color = PURPLE;
			break;
		case 4:
			color = ORANGE;
			break;
		}
		return color;
	}
	
	private String generateItemName() {
		return "Item Name";
	}
	
	@SuppressWarnings("unused")
	private TextureRegion generateItemTextureRegion(){
		TextureRegion reg = null;
		int item_code;
		switch (this.getItemType()) {
		case SWORD:
			item_code = MathUtils.random(0, SWORD_SKINS_COUNT-1);
			if (DEBUG) System.out.println("item code: "+item_code);
			switch (item_code) {
			case 0:
				reg = Assets.instance.swords.sword00;
				break;
			case 1:
				reg = Assets.instance.swords.sword01;
				break;
			}
		}
		if (DEBUG && reg == null) System.out.println("item texture region is null");
		return reg;
	}
	
	public Window getItemWindow() {

		Skin skin = new Skin (Gdx.files.internal(Constants.SKIN_LOOTGAME_UI));
		
		Window itemWindow = new Window("", skin);
		itemWindow.setBackground(new Image(new TextureRegion(Assets.instance.misc.itemWindowBackground)).getDrawable());
		itemWindow.setSize(110, 220);
		Label titleLabel = new Label(itemName, skin, "default-font", itemRarityColor);
		Image icon = new Image(itemTexture);
		String statsLabelText = itemType.toString()+"\n";
		HashMap<String, Integer> stats = itemStats.getStats();
		for (String s : ItemStats.itemStatList) {
			statsLabelText += s + ": " + stats.get(s) + "\n";
		}
		Label statsLabel = new Label(statsLabelText, skin, "default-font", WHITE);
		
		
		//layer.add(itemTitleLabel);
		itemWindow.add(titleLabel).row();
		itemWindow.add(icon).row();
		itemWindow.add(statsLabel).row();
		return itemWindow;
	}
	
	public TextureRegion getItemTexture() {
		return itemTexture;
	}
	
	public ItemStats getItemStats() {
		return itemStats;
	}
	
	public ItemTypes getItemType() {
		return itemType;
	}
	
}
