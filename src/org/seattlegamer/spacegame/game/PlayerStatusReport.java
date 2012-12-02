package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Message;

public class PlayerStatusReport implements Message {

	private int health;

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}
