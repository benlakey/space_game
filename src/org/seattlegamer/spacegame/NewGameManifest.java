package org.seattlegamer.spacegame;

import java.util.ArrayList;
import java.util.List;

public class NewGameManifest {

	private final List<String> players;
	
	public NewGameManifest() {
		this.players = new ArrayList<String>();
	}

	public List<String> getPlayers() {
		return players;
	}
	
}
