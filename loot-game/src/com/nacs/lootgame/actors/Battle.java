package com.nacs.lootgame.actors;

import java.util.ArrayList;
import java.util.HashMap;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Battle {
		
	private ACTION_KEY playerActionA, playerActionB, playerActionC, playerActionD;
	
	public static final float[] action_durations = {1.08f};
	
	public static enum ACTION_KEY {
		Attack01,
		Attack02,
		Attack03,
		HealSelf
	}
	
	public Battle(BattleActor player, ArrayList<BattleActor> enemies) {
		
		playerActionA = ACTION_KEY.values()[player.getActionA()];
		playerActionB = ACTION_KEY.values()[player.getActionB()];
		playerActionC = ACTION_KEY.values()[player.getActionC()];
		playerActionD = ACTION_KEY.values()[player.getActionD()];
		
	}
		
	// 1: Attack01
	public String actionOne(BattleActor initiator, Actor initiatorActor, BattleActor target, Actor targetActor) {
		int damage = initiator.getAttackPower();
		target.modifyHealth(-damage);
		
		float initialX = initiatorActor.getX();
		float initialY = initiatorActor.getY();
		
		initiatorActor.addAction(sequence(
				Actions.moveTo(targetActor.getX() - 85.0f, targetActor.getY(), 0.75f),
				
				Actions.moveTo(initialX, initialY, 0.33f)
				));
		
		String battle_text = "";
		battle_text += initiator.getName() + " damages " + target.getName() + " " + damage + " points!";
		return battle_text;
	}
	
	// 2: Attack02
	public void actionTwo(BattleActor initiator, ArrayList<BattleActor> targets) {
		for (BattleActor a : targets) {
			a.modifyHealth((int)(-0.5 * initiator.getAttackPower()));
		}
	}
	
	// 3: Attack03
	public void actionThree(BattleActor initiator, BattleActor target) {
		target.modifyHealth(-1 * MathUtils.random(0, initiator.getAttackPower()*2));
	}
	// 4: HealSelf
	public void actionFour(BattleActor initiator) {
		initiator.modifyHealth(50);
	}
}
