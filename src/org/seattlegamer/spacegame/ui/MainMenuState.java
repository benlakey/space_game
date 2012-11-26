package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.ExitStateCommand;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.LoadStateCommand;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.game.GameState;

public class MainMenuState implements State {

	private final Collection<Entity> entities;
	
	public MainMenuState() {
		this.entities = new LinkedList<Entity>();
	}

	@Override
	public void load(Handler stateChangeHandler) {

		Entity mainMenu = new MenuBuilder()
			.addEntry("New Game", new LoadStateCommand(new GameState()))
			.addEntry("Exit", new ExitStateCommand())
			.build();
		
		mainMenu.registerHandler(stateChangeHandler);
	
		this.entities.add(mainMenu);

	}

	@Override
	public Iterable<Entity> getEntities() {
		return this.entities;
	}

}
