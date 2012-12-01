package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.StateControlInput;

public class MenuBuilder {

	private Entity menu;
	private int currentIndex = 0;
	
	public MenuBuilder() {
		this.menu = new Entity();
	}

	public MenuBuilder addEntry(String text, Message message) {
		
		MenuEntry entry = new MenuEntry(this.menu, this.currentIndex, message);
		MenuEntryRenderer renderer = new MenuEntryRenderer(this.menu, this.currentIndex, text);
		
		this.menu.register(renderer);
		this.menu.register(entry);
		
		this.currentIndex++;
		
		return this;

	}
	
	public Entity build() {
		this.menu.register(new StateControlInput(this.menu));
		this.menu.register(new MenuInput(this.menu, 0, currentIndex + 1));
		this.menu.broadcast(MenuEntryChange.class, new MenuEntryChange(0));
		return this.menu;
	}
	
}
