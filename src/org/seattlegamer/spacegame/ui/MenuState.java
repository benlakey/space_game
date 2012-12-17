package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.ComponentBus;
import org.seattlegamer.spacegame.core.GameState;
import org.seattlegamer.spacegame.core.Input;
import org.seattlegamer.spacegame.core.Renderer;
import org.seattlegamer.spacegame.core.State;
import org.seattlegamer.spacegame.core.StateManager;
import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class MenuState implements State {
	
	private static final Logger logger = Logger.getLogger(MenuState.class);

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
				manifest.getPlayers().add("Joe");
				
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
	public void removeComponent(Component component) {
		this.components.remove(component);
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
