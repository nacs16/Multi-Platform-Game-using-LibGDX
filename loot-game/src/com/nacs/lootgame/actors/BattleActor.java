package com.nacs.lootgame.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.nacs.lootgame.game.Assets;
import com.nacs.lootgame.loot.Item;

public class BattleActor {

	private String name;
	private int health;
	private int attack_power;
	private TextureRegion actorTexture;
	private ActorStats actorStats;
	public HashMap<String, Integer> stats;
	private int actionA, actionB, actionC, actionD;
	private Item actorItem;
	private ActorType actorType;
	
	public static enum ActorType { PLAYER, ENEMY00 };	
	
	public BattleActor(ActorType type, Item item, String name) {
		this.name = name;
		actionA = 0;
		actionB = 1;
		actionC = 2;
		actionD = 3;
		actorStats = new ActorStats();
		stats = actorStats.getStats();
		health = stats.get("max_health");
		attack_power = stats.get("attack_power");
		actorItem = item;
		actorType = type;
		switch(type){
		case PLAYER:
			actorTexture = Assets.instance.pc.pc00;
			break;
		case ENEMY00:
			actorTexture = Assets.instance.enemies.enemy00;
			break;
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public Group getActorGroup() {
		Group layer = new Group();
		
		TextureRegion actorImageTexture = new TextureRegion(actorTexture);
		TextureRegion itemImageTexture = new TextureRegion(actorItem.getItemTexture());
		if (actorType == ActorType.PLAYER) {	
			actorImageTexture.flip(false, false);
		} else {
			itemImageTexture.flip(true, false);
		}
		Image actorImage = new Image(actorImageTexture);
		Image itemImage = new Image(itemImageTexture);
		
		actorImage.setName("actorImage");
		itemImage.setName("itemImage");
				
		layer.setSize(actorImage.getWidth() + itemImage.getWidth(), actorImage.getHeight() + itemImage.getHeight());
		
		layer.addActor(actorImage);
		layer.addActor(itemImage);
		
		if (actorType == ActorType.PLAYER) {
			layer.findActor("itemImage").setPosition(85.0f, 48.0f);
		} else {
			layer.findActor("itemImage").setPosition(itemImage.getImageWidth() - 45.0f, 67.0f);
		}
		
		layer.setName(name);
		return layer;
	}

	public int getActionA() {
		return actionA;
	}
	
	public int getActionB() {
		return actionB;
	}
	
	public int getActionC() {
		return actionC;
	}
	
	public int getActionD() {
		return actionD;
	}
	
	public void modifyHealth(int amount) {
		health += amount;
	}
	
	public int getAttackPower() {
		return attack_power;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isAlive() {
		boolean isAlive = true;
		if (health < 0) isAlive = false;
		return isAlive;
	}
	
	
}
