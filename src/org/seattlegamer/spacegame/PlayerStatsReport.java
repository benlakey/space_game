package org.seattlegamer.spacegame;

import java.util.UUID;

public class PlayerStatsReport {

	private final UUID playerEntityId;
	private final String name;
	private final int health;
	
	public PlayerStatsReport(UUID playerEntityId, String name, int health) {
		this.playerEntityId = playerEntityId;
		this.name = name;
		this.health = health;
	}

	public UUID getPlayerEntityId() {
		return this.playerEntityId;
	}

	public String getName() {
		return this.name;
	}

	public int getHealth() {
		return this.health;
	}
	
}
