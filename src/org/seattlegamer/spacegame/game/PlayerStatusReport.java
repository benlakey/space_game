package org.seattlegamer.spacegame.game;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusReport extends Message {

	private int health;
	
	protected PlayerStatusReport(UUID sourceEntityId) {
		super(sourceEntityId);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}
