package org.seattlegamer.spacegame.game;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusReport implements Message {

	private final UUID playerEntityId;
	private final int health;
	
	protected PlayerStatusReport(UUID playerEntityId, int health) {
		this.playerEntityId = playerEntityId;
		this.health = health;
	}
	
	public UUID getPlayerEntityId() {
		return playerEntityId;
	}

	public int getHealth() {
		return health;
	}

}
