package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.menu.MenuBuilder;

public class StateManager implements Handler {

	private final Collection<Entity> entities;
	
	public StateManager() {
		this.entities = new LinkedList<Entity>();
	}
	
	public Iterable<Entity> getEntities() {
		return this.entities;
	}

	//TODO: attach me somewhere so i can operate
	@Override
	public void handle(Message message) {
		if(message instanceof ExitGame) {
			System.exit(0);
		} else if(message instanceof NewGame) {
			//TODO: clear game entities
			//TODO: build new game entities based on params from message
			//TODO: switch to game
		} else if(message instanceof OpenMenu) {
			//TODO: hide game entities, if present
			//TODO: build menu entities, if necessary
			//TODO: switch to menu
			this.loadMainMenu(); //short term hack only. see previous 3 lines
		}
	}
	
	private void loadMainMenu() {
		
		Entity mainMenu = new MenuBuilder()
			.addEntry("New Game", new NewGame())
			.addEntry("Exit", new ExitGame())
			.build();
		
		mainMenu.registerHandler(this);
	
		this.entities.add(mainMenu);

	}
	
}
