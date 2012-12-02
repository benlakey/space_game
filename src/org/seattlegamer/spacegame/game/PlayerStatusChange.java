package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusChange implements Message {

	private final int healthOffset;
	
	public PlayerStatusChange(int healthOffset) {
		this.healthOffset = healthOffset;
	}

	public int getHealthOffset() {
		return this.healthOffset;
	}

}
