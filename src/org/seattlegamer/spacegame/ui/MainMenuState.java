package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.ExitStateCommand;
import org.seattlegamer.spacegame.LoadStateCommand;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.game.GameState;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class MainMenuState extends State {

	@Override
	public void load(ResourceCache resourceCache) {

		Iterable<Entity> mainMenu = new MenuBuilder(2)
			.addEntry("New Game", new LoadStateCommand(new GameState()))
			.addEntry("Exit", new ExitStateCommand())
			.build();

		for(Entity entity : mainMenu) {
			this.registerEntity(entity);
		}

	}

}
