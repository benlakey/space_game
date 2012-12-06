package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ExitGameCommand;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateSwitch;
import org.seattlegamer.spacegame.game.NewGameManifest;

public class MenuState extends State {

	private static final NewGameManifest hardcodedNewGameManifest = new NewGameManifest("John Doe", "Bob Smith");

	public MenuState(final Bus bus, MenuType menuType) {
		super(bus);
		switch(menuType) {
			case MAIN_MENU: this.loadMainMenu(); break;
			case MAIN_MENU_WITH_RESUME_GAME: this.loadResumeMainMenu(); break;
		}

	}

	private void loadMainMenu() {
		
		this.components.clear();
		
		Iterable<Component> components = new MenuBuilder(this.bus)
			.addEntry("New Game", hardcodedNewGameManifest)
			.addEntry("Exit", new ExitGameCommand())
			.build();

		for(Component component : components) {
			this.components.add(component);
		}
		
		this.activationCommands.add(new ActivationCommand() {
			@Override
			public void execute() {
				bus.broadcast(new MenuEntryChange(0));
			}
		});

	}
	
	private void loadResumeMainMenu() {
		
		this.components.clear();

		Iterable<Component> components = new MenuBuilder(this.bus)
			.addEntry("Resume Game", StateSwitch.GAME)
			.addEntry("New Game", hardcodedNewGameManifest)
			.addEntry("Exit", new ExitGameCommand())
			.build();

		for(Component component : components) {
			this.components.add(component);
		}
		
		this.activationCommands.add(new ActivationCommand() {
			@Override
			public void execute() {
				bus.broadcast(new MenuEntryChange(0));
			}
		});
		
	}

}
