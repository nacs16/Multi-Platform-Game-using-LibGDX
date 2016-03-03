package com.nacs.lootgame.loot;

import java.util.HashMap;
import com.badlogic.gdx.math.MathUtils;

public class ItemStats {
	
	/*
	 * allAttributes Outline:
	 * 	0 - attribute00
	 *  1 - itemScore
	 */
	
	private int itemScore;
	private int attribute00;
	public HashMap<String, Integer> stats;
	public static final String[] itemStatList = {"attribute00"};
	
	public ItemStats() {
		stats = new HashMap<String, Integer>();
		attribute00 = MathUtils.random(0, 100);
		stats.put("attribute00", attribute00);
		itemScore = scoreItem();
		stats.put("score", itemScore);
	}
	
	private int scoreItem() {
		int score = 0;
		
		
		
		
		return score;
	}
	
	public HashMap<String, Integer> getStats() {
		return stats;
	}
	
}
