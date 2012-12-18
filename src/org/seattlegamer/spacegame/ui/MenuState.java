package org.seattlegamer.spacegame.ui;

import java.awt.Font;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.ComponentBus;
import org.seattlegamer.spacegame.core.GameState;
import org.seattlegamer.spacegame.core.KeyInput;
import org.seattlegamer.spacegame.core.PointerInput;
import org.seattlegamer.spacegame.core.Renderer;
import org.seattlegamer.spacegame.core.State;
import org.seattlegamer.spacegame.core.StateManager;
import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class MenuState implements State {
	
	private static final Logger logger = Logger.getLogger(MenuState.class);
	private static final int MENU_FONT_SIZE = 64;

	private final ComponentBus bus;
	private final Collection<Component> components;
	private final UUID menuEntityId = UUID.randomUUID();

	public MenuState(ComponentBus bus) {
		this.bus = bus;
		this.components = new LinkedList<Component>();
	}
	
	@Override
	public void load(ResourceCache resourceCache, StateManager stateManager, GameSettings settings) {
		
		this.components.clear();

		Iterable<Component> menuComponents = new MenuBuilder(bus)
			.setFont(new Font(settings.getFont(), Font.BOLD, MENU_FONT_SIZE))
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
			public void execute() {
				
				NewGameManifest manifest = new NewGameManifest();
				manifest.getPlayers().add("Bob");
				manifest.getPlayers().add("Joe");
				
				stateManager.changeState(new GameState(bus, manifest));

			}
		};
		
	}
	
	private MenuAction getExitGameMenuAction() {
		
		return new MenuAction() {
			@Override
			public void execute() {
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
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		for(Component component : this.components) {
			component.update(keyInput, pointerInput, elapsedMillis);
		}
	}

	@Override
	public void render(Renderer renderer) {
		renderer.render(this.components);
	}

}
