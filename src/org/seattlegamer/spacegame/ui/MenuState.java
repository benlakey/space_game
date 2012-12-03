package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.game.GameState;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class MenuState extends State {

	@Override
	public void load(Bus bus, ResourceCache resourceCache) {

		Iterable<Component> mainMenu = new MenuBuilder(bus)
			.addStateLoadEntry("New Game", new GameState())
			.addStateExitEntry("Exit")
			.build();

		for(Component component : mainMenu) {
			this.components.add(component);
		}

	}

}
