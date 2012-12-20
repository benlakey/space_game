package org.seattlegamer.spacegame.core;

import java.io.IOException;

import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.messages.StateChange;

public class GameLauncher {

	private final Bus bus;
	private final GameState gameState;
	
	public GameLauncher(Bus bus, GameState gameState) {
		this.bus = bus;
		this.gameState = gameState;
	}
	
	@Subscription
	public void startNewGame(NewGameManifest manifest) throws IOException {
		this.gameState.loadNewGame(manifest);
		this.bus.broadcast(new StateChange(GameState.class));
	}
	
}
