package org.seattlegamer.spacegame.game;

import java.awt.Point;
import java.io.IOException;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;
import org.seattlegamer.spacegame.PositionInitialization;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class GameState extends State {

	@Override
	public void load(ResourceCache resourceCache) throws IOException {

		Entity player1 = new Entity();
		//player1.register(new HeadsUpDisplay(player1, 1, "John Doe"));
		player1.register(new PlayerStatus(player1));
		player1.register(new StateControlInput(player1));
		player1.register(new Position(player1));
		player1.register(new Sprite(player1, resourceCache.getImage("assets/mars.png")));
		
		player1.broadcast(PositionInitialization.class, 
				new PositionInitialization(new Point(0, 0), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE));

		this.registerEntity(player1);
		
		Entity player2 = new Entity();
		//player2.register(new HeadsUpDisplay(player2, 2, "Bob Smith"));
		player2.register(new PlayerStatus(player2));
		player2.register(new StateControlInput(player2));
		player2.register(new Position(player2));
		player2.register(new Sprite(player2, resourceCache.getImage("assets/mars.png")));
		
		player2.broadcast(PositionInitialization.class, 
				new PositionInitialization(new Point(0, 0), HorizontalAlignment.LEFT, VerticalAlignment.TOP));

		this.registerEntity(player2);

	}

}
