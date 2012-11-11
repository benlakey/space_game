package org.seattlegamer.spacegame;

public class Player {

	private static final int START_HEALTH = 100;

	private final String name;
	private int health;

	public Player(String name) {
		this.name = name;
		this.health = START_HEALTH;
	}
	
	public String getName() {
		return this.name;
	}

	public int getHealth() {
		return this.health;
	}

}
