package org.seattlegamer.spacegame.communication;

public class HealthReport implements Command {

	private final String name;
	private final int health;

	public HealthReport(String name, int health) {
		this.name = name;
		this.health = health;
	}

	public String getName() {
		return name;
	}
	
	public int getHealth() {
		return this.health;
	}

}
