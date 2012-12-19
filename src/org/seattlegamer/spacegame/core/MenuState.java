package org.seattlegamer.spacegame.core;

import java.util.UUID;

import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.messages.NewGameManifest;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class MenuState extends State {

	private final UUID menuEntityId;
	private boolean mainMenuLoaded;

	public MenuState(Bus bus, ResourceCache resourceCache) {
		super(bus, resourceCache);
		this.menuEntityId = UUID.randomUUID();
	}

	@Override
	public void onActivate() {

		super.onActivate();

		if(this.mainMenuLoaded) {
			return;
		}
		
		this.loadMainMenu();
		
		this.mainMenuLoaded = true;

	}
	
	@Override
	public void onDeactivate() {
		this.bus.deregister(this, null);
		super.onDeactivate();
	}
	
	private void loadMainMenu() {

		Iterable<Component> menuComponents = new MenuBuilder(bus)
			.addMenuEntry("New Game", this.getHardcodedNewGameMenuAction())
			.addMenuEntry("Exit", this.getExitGameMenuAction())
			.build(this.menuEntityId);
		
		for(Component component : menuComponents) {
			this.bus.broadcast(new ComponentAddition(component));
		}
	
		bus.send(new MenuSelectionChanged(0), this.menuEntityId);
		
	}

	private MenuAction getHardcodedNewGameMenuAction() {
		
		return new MenuAction() {
			@Override
			public void execute() {
				
				NewGameManifest manifest = new NewGameManifest();
				manifest.getPlayers().add("Bob");
				manifest.getPlayers().add("Joe");

				bus.broadcast(manifest);

			}
		};
		
	}
	
	private MenuAction getExitGameMenuAction() {
		
		return new MenuAction() {
			@Override
			public void execute() {
				System.exit(0);
			}
		};
		
	}

}
