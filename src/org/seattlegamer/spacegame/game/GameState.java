package org.seattlegamer.spacegame.game;

import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;

public class GameState implements State {

	private final Collection<Entity> entities;
	
	public GameState() {
		this.entities = new LinkedList<Entity>();
	}
	
	@Override
	public void load(Handler stateChangeHandler) {
		
		Entity player = new Entity();
		player.add(new HeadsUpDisplayEntryRenderer(player, 1, "John Doe"));
		player.add(new StateControlInput(player));
		player.handle(new PlayerStatusChange(100));

		player.registerHandler(stateChangeHandler);
		
		this.entities.add(player);

	}

	@Override
	public Iterable<Entity> getEntities() {
		return this.entities;
	}

}
