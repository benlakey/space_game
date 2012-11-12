package org.seattlegamer.spacegame;

public class Player {

	private static final int START_HEALTH = 100;

	private final String name;
	private int health;
	private SpaceBody spaceBody;

	public Player(String name, SpaceBody planet) {
		this.name = name;
		this.health = START_HEALTH;
		this.spaceBody = planet;
	}
	
	public String getName() {
		return this.name;
	}

	public int getHealth() {
		return this.health;
	}
	
	public SpaceBody getSpaceBody() {
		return this.spaceBody;
	}

}
