package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.entities.Player;

public class HealthReport implements Command {

	private final Player player;

	public HealthReport(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
