package org.seattlegamer.spacegame.game;

import java.awt.Point;
import java.io.IOException;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;
import org.seattlegamer.spacegame.PositionInitialization;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.HeadsUpDisplay;

public class GameState extends State {

	@Override
	public void load(Bus bus, ResourceCache resourceCache) throws IOException {

		this.addPlayer(bus, resourceCache, 1, "John Doe");
		this.addPlayer(bus, resourceCache, 2, "Bob Smith");

	}
	
	private void addPlayer(Bus bus, ResourceCache resourceCache, int playerNumber, String name) throws IOException {
		
		UUID playerId = UUID.randomUUID();
		UUID hudId = UUID.randomUUID();

		this.components.add(new HeadsUpDisplay(bus, hudId, playerId, playerNumber, name));
		this.components.add(new Position(bus, hudId));

		this.components.add(new PlayerStatus(bus, playerId));
		this.components.add(new StateControlInput(bus, playerId));
		this.components.add(new Position(bus, playerId));
		this.components.add(new Sprite(bus, playerId, resourceCache.getImage("assets/mars.png")));

		bus.send(new PositionInitialization(new Point(0, 0), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE), playerId);
		
	}

}
