package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusChange implements Message {

	private final int health;
	
	public PlayerStatusChange(int health) {
		this.health = health;
	}

	public int getHealth() {
		return this.health;
	}

}
