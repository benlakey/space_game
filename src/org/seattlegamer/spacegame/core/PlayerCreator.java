package org.seattlegamer.spacegame.core;

import java.awt.DisplayMode;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.PlayerStatsReport;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class PlayerCreator {

	//TODO: move to a component that handles player health, when such a component exists (will be the component that takes damage and reports health to HUD)
	private static final int STARTING_HEALTH = 100;
	
	private final Bus bus;
	private final ResourceCache resourceCache;
	private final DisplayMode displayMode;

	public PlayerCreator(Bus bus, ResourceCache resourceCache, DisplayMode displayMode) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.displayMode = displayMode;
	}

	public void createPlayer(int playerNumber, String name) throws IOException {
		
		UUID playerEntityId = UUID.randomUUID();
		UUID hudEntityId = UUID.randomUUID();

		Random random = new Random();

		Point position = new Point(
				random.nextInt(this.displayMode.getWidth()), 
				random.nextInt(this.displayMode.getHeight()));

		Image player = this.resourceCache.getImage("replaceme_hardcoded_planet");

		this.bus.broadcast(new ComponentAddition(new Sprite(bus, playerEntityId, player)));
		this.bus.broadcast(new ComponentAddition(new Physics(bus, playerEntityId, position, 0, player.getWidth(null), player.getHeight(null))));
		this.bus.broadcast(new ComponentAddition(new ProjectileLauncher(bus, playerEntityId, this.resourceCache)));
		this.bus.broadcast(new ComponentAddition(new HeadsUpDisplayEntryRenderer(bus, hudEntityId, playerEntityId, playerNumber)));
		
		//TODO: hack for testing. input will be assigned turn-based.
		if(playerNumber == 1) {
			this.bus.broadcast(new ComponentAddition(new PlayerInput(bus, playerEntityId)));
		}

		bus.send(new PlayerStatsReport(playerEntityId, name, STARTING_HEALTH), hudEntityId);

	}
	
}
