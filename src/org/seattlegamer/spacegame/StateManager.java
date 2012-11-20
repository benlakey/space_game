package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.game.PlayerStatusChange;
import org.seattlegamer.spacegame.ui.HeadsUpDisplayEntryRenderer;
import org.seattlegamer.spacegame.ui.MenuBuilder;

public class StateManager implements Handler {

	private static Logger logger = Logger.getLogger(StateManager.class);
	
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
		
		if(message instanceof StateChange) {
			StateChange stateChange = (StateChange)message;
			switch(stateChange) {
				case NEW_GAME: this.loadNewGame(); break;
				case OPEN_MENU: this.loadMainMenu(); break;
				case CLOSE_MENU: this.hideMainMenu(); break;
				case EXIT: System.exit(0); break;
			}
		}

	}
	
	private void hideMainMenu() {
		
		this.setEnabled(ComponentGroup.MENU, false);
		this.setEnabled(ComponentGroup.GAME, true);

	}

	//TODO: this is just here temporarily. Move to an abstraction.
	private void loadNewGame() {

		logger.info("Starting a new game.");
		
		this.entities.clear();

		Entity player = new Entity();
		player.add(new HeadsUpDisplayEntryRenderer(player, 1, "John Doe"));
		player.handle(new PlayerStatusChange(100));

		player.registerHandler(this);
		
		this.entities.add(player);

		this.setEnabled(ComponentGroup.GAME, true);

		//TODO: build new game entities based on params from the message

	}
	
	//TODO: this is just here temporarily. Move to an abstraction.
	private void loadMainMenu() {
		
		logger.info("Opening the menu.");
		
		this.setEnabled(ComponentGroup.GAME, false);
		
		//TODO: dont load every time. cache if entities exist already
		Entity mainMenu = new MenuBuilder()
			.addEntry("New Game", StateChange.NEW_GAME)
			.addEntry("Exit", StateChange.EXIT)
			.build();
		
		mainMenu.registerHandler(this);
	
		this.entities.add(mainMenu);
		
		this.setEnabled(ComponentGroup.MENU, true);

	}
	
	private void setEnabled(ComponentGroup group, boolean enabled) {
		for(Entity entity : this.entities) {
			entity.setEnabled(enabled);
		}
	}
	
}
