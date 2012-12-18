package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.messages.PlayerStatsReport;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class PlayerCreator {
	
	//TODO: move to a component that handles player health, when such a component exists (will be the component that takes damage and reports health to HUD)
	private static final int STARTING_HEALTH = 100;
	
	private final Bus<Component> bus;
	private final ResourceCache resourceCache;
	private final StateManager stateManager;
	private final GameSettings settings;

	public PlayerCreator(Bus<Component> bus, ResourceCache resourceCache, StateManager stateManager, GameSettings settings) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.stateManager = stateManager;
		this.settings = settings;
	}

	public Iterable<Component> createPlayer(int playerNumber, String name) throws IOException {

		Collection<Component> components = new LinkedList<Component>();
		
		UUID playerEntityId = UUID.randomUUID();

		Random random = new Random();

		Point position = new Point(
				random.nextInt(settings.getDisplayModeWidth()), 
				random.nextInt(settings.getDisplayModeHeight()));

		Image player = TestImageCreator.buildPlayer(Color.BLUE);
		Image scaledPlayer = GraphicsUtils.getScaledImage(player, settings.getScale());
		
		components.add(new Sprite(bus, playerEntityId, scaledPlayer));
		components.add(new Physics(bus, playerEntityId, position, 0, scaledPlayer.getWidth(null), scaledPlayer.getHeight(null)));
		components.add(new ProjectileLauncher(bus, playerEntityId, resourceCache, stateManager, settings));
		
		//TODO: hack for testing. input will be assigned turn-based.
		if(playerNumber == 1) {
			components.add(new PlayerInput(bus, playerEntityId, stateManager));
		}
		
		UUID hudEntityId = UUID.randomUUID();
		Font hudFont = new Font(settings.getFont(), Font.PLAIN, 32);
		components.add(new HeadsUpDisplayEntryRenderer(bus, hudEntityId, playerEntityId, playerNumber, hudFont));
		bus.send(new PlayerStatsReport(playerEntityId, name, STARTING_HEALTH), hudEntityId);
		
		return components;

	}
	
}
