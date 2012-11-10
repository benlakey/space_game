package org.seattlegamer.spacegame;

public class Player {

	private static final int START_HEALTH = 100;

	private int health;
	
	public Player() {
		this.health = START_HEALTH;
	}

	public int getHealth() {
		return this.health;
	}

}
