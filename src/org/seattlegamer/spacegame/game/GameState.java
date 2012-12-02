package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;

public class GameState extends State {

	@Override
	public void load() {

		Entity player1 = new Entity();
		player1.register(new HeadsUpDisplayEntryRenderer(player1, 1, "John Doe"));
		player1.register(new PlayerStatus(player1));
		player1.register(new StateControlInput(player1));
		player1.register(new Position(player1));

		this.registerEntity(player1);
		
		Entity player2 = new Entity();
		player2.register(new HeadsUpDisplayEntryRenderer(player2, 2, "Bob Smith"));
		player2.register(new PlayerStatus(player2));
		player2.register(new StateControlInput(player2));
		player2.register(new Position(player2));

		this.registerEntity(player2);

	}

}
