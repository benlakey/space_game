package org.seattlegamer.spacegame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.game.GameState;
import org.seattlegamer.spacegame.game.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.ui.MenuType;

public class StateManager {

	private static Logger logger = Logger.getLogger(StateManager.class);
	
	private final Bus bus;
	private final ResourceCache resourceCache;
	private Map<StateSwitch, State> states;
	private State currentState;
	
	public StateManager(Bus bus, ResourceCache resourceCache) {

		this.bus = bus;
		this.resourceCache = resourceCache;
		this.states = new HashMap<StateSwitch, State>();

		this.bus.register(StateSwitch.class, this.getStateSwitchHandler());
		this.bus.register(NewGameManifest.class, this.getNewGameManifestHandler());
		this.bus.register(ExitGameCommand.class, this.getExitGameCommandHandler());

		this.states.put(StateSwitch.MAIN_MENU, new MenuState(bus, MenuType.MAIN_MENU));
		this.states.put(StateSwitch.MAIN_MENU_WITH_RESUME, new MenuState(bus, MenuType.MAIN_MENU_WITH_RESUME_GAME));

		this.bus.broadcast(StateSwitch.MAIN_MENU);

	}
	
	public Iterable<Component> getComponents() {
		return this.currentState.getComponents();
	}
	
	private Handler<StateSwitch> getStateSwitchHandler() {

		return new Handler<StateSwitch>() {
			
			@Override
			public void handle(StateSwitch message) {

				if(states.containsKey(message)) {

					if(currentState != null) {
						currentState.deactivate();
					}
					
					currentState = states.get(message);
					currentState.activate();

				}

			}
			
			@Override
			public UUID getEntityIdHandlingFor() {
				return null;
			}

		};
	}

	private Handler<NewGameManifest> getNewGameManifestHandler() {

		return new Handler<NewGameManifest>() {
			
			@Override
			public void handle(NewGameManifest message) {

				GameState gameState = new GameState(bus, message);
				try {
					gameState.load(resourceCache);
				} catch (IOException e) {
					logger.fatal("Game failed to load.", e);
					System.exit(-1);
				}
				states.put(StateSwitch.GAME, gameState);
				
				bus.broadcast(StateSwitch.GAME);

			}
			
			@Override
			public UUID getEntityIdHandlingFor() {
				return null;
			}

		};
	}

	private Handler<ExitGameCommand> getExitGameCommandHandler() {
		return new Handler<ExitGameCommand>() {

			@Override
			public void handle(ExitGameCommand message) {
				System.exit(0);
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return null;
			}

		};
	}

}
