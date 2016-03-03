package com.nacs.lootgame.actors;

import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;

public class ActorStats {
	
	private int actorScore; // level
	private int max_health;
	private int attack_power;
	public HashMap<String, Integer> stats;
	public static final String[] actorStatList = {"max_health"};
	
	public ActorStats() {
		stats = new HashMap<String, Integer>();
		max_health = MathUtils.random(100, 300);
		attack_power = MathUtils.random(10,50);
		actorScore = scoreActor();
		stats.put("max_health", max_health);
		stats.put("attack_power", attack_power);
		stats.put("score", actorScore);
	}
	
	private int scoreActor() {
		int score = 0;
		
		
		return score;
	}
	
	public HashMap<String, Integer> getStats() {
		return stats;
	}
	
}
