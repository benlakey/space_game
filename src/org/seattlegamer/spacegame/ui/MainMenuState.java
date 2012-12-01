package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.ExitStateCommand;
import org.seattlegamer.spacegame.LoadStateCommand;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.game.GameState;

public class MainMenuState extends State {

	@Override
	public void load() {

		Entity mainMenu = new MenuBuilder()
			.addEntry("New Game", new LoadStateCommand(new GameState()))
			.addEntry("Exit", new ExitStateCommand())
			.build();

		this.registerEntity(mainMenu);

	}

}
