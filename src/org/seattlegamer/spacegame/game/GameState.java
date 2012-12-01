package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;

public class GameState extends State {

	@Override
	public void load() {
		
		Entity player = new Entity();
		player.register(new HeadsUpDisplayEntryRenderer(player, 1, "John Doe"));
		player.register(new StateControlInput(player));
		player.broadcast(PlayerStatusChange.class, new PlayerStatusChange(100));

		this.registerEntity(player);

	}

}
