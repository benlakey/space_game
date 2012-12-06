package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Message;

public class NewGameManifest implements Message {

	private final String player1Name;
	private final String player2Name;
	
	public NewGameManifest(String player1Name, String player2Name) {
		this.player1Name = player1Name;
		this.player2Name = player2Name;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}
	
}
