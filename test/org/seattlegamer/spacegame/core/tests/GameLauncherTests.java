package org.seattlegamer.spacegame.core.tests;

import static org.junit.Assert.assertTrue;

import java.awt.DisplayMode;
import java.awt.Image;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.BusImpl;
import org.seattlegamer.spacegame.core.GameLauncher;
import org.seattlegamer.spacegame.core.GameState;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class GameLauncherTests {

	@Test
	public void newGameCreatedWithPlayersFromManifest() throws IOException {
		
		Bus bus = new BusImpl();
		//TODO: use Mockito for dependencies
		ResourceCache resourceCache = new ResourceCache() {
			@Override public void putImage(String name, Object asset) {}
			@Override public Image getImage(String name) throws IOException { return null; }
		};
		DisplayMode displayMode = new DisplayMode(800, 600, 16, 60);
		MockGameState gameState = new MockGameState(bus, resourceCache, displayMode);
		GameLauncher launcher = new GameLauncher(bus, gameState);
		
		Collection<String> players = new LinkedList<String>();
		players.add("Bob");
		players.add("Joe");
		
		NewGameManifest manifest = new NewGameManifest();
		for(String player : players) {
			manifest.getPlayers().add(player);
		}
		
		launcher.startNewGame(manifest);
		
		Collection<String> gameStatePlayers = gameState.getPlayers();

		for(String player : players) {
			assertTrue(gameStatePlayers.contains(player));
		}

	}
	
	//TODO: use Mockito
	private class MockGameState extends GameState {
		
		private final Collection<String> players;

		public MockGameState(Bus bus, ResourceCache resourceCache, DisplayMode displayMode) {
			super(bus, resourceCache, displayMode);
			this.players = new LinkedList<String>();
		}
		
		@Override
		public void loadNewGame(NewGameManifest manifest) {
			this.players.clear();
			List<String> newPlayers = manifest.getPlayers();
			for(String player : newPlayers) {
				this.players.add(player);
			}
		}

		public Collection<String> getPlayers() {
			return this.players;
		}
		
	}
	
}
