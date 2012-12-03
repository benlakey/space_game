package org.seattlegamer.spacegame.game;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusChange extends Message {

	private final int healthOffset;
	
	public PlayerStatusChange(UUID sourceEntityId, int healthOffset) {
		super(sourceEntityId);
		this.healthOffset = healthOffset;
	}

	public int getHealthOffset() {
		return this.healthOffset;
	}

}
