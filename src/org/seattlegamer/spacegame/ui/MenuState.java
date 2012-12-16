package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.GameState;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.NewGameManifest;
import org.seattlegamer.spacegame.Renderer;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateManager;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.utils.Throttle;

public class MenuState implements State {
	
	private static final Logger logger = Logger.getLogger(MenuState.class);
	
	private static final int MENU_TOGGLE_DELAY_MILLIS = 300;
	public static final Throttle menuToggleThrottle = new Throttle(MENU_TOGGLE_DELAY_MILLIS);

	private final Collection<Component> components;
	private final UUID menuEntityId = UUID.randomUUID();

	public MenuState() {
		this.components = new LinkedList<Component>();
	}
	
	@Override
	public void load(ComponentBus bus, ResourceCache resourceCache, StateManager stateManager) {
		
		this.components.clear();

		Iterable<Component> menuComponents = new MenuBuilder(bus)
			.addMenuEntry("New Game", this.getHardcodedNewGameMenuAction(stateManager))
			.addMenuEntry("Exit", this.getExitGameMenuAction())
			.build(this.menuEntityId, stateManager);
		
		for(Component component : menuComponents) {
			this.components.add(component);
		}
	
		bus.send(new MenuSelectionChanged(0), this.menuEntityId);
		
	}

	private MenuAction getHardcodedNewGameMenuAction(final StateManager stateManager) {
		
		return new MenuAction() {
			@Override
			public void execute(ComponentBus bus) {
				
				NewGameManifest manifest = new NewGameManifest();
				manifest.getPlayers().add("Bob");
				
				stateManager.changeState(new GameState(manifest));

			}
		};
		
	}
	
	private MenuAction getExitGameMenuAction() {
		
		return new MenuAction() {
			@Override
			public void execute(ComponentBus bus) {
				logger.info("exiting game");
				System.exit(0);
			}
		};
		
	}
	
	@Override
	public void addComponent(Component component) {
		this.components.add(component);
	}

	@Override
	public void update(Input input, long elapsedMillis) {
		for(Component component : this.components) {
			component.update(input, elapsedMillis);
		}
	}

	@Override
	public void render(Renderer renderer) {
		renderer.render(this.components);
	}

}
