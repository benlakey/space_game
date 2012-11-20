package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Message;

public class MenuBuilder {

	private Entity menu;
	private int currentIndex = 0;
	
	public MenuBuilder() {
		this.menu = new Entity();
	}

	public MenuBuilder addEntry(String text, Message message) {
		
		MenuEntry entry = new MenuEntry(this.menu, this.currentIndex, message);
		MenuEntryRenderer renderer = new MenuEntryRenderer(this.menu, this.currentIndex, text);
		
		this.menu.add(renderer);
		this.menu.add(entry);
		
		this.currentIndex++;
		
		return this;

	}
	
	public Entity build() {
		this.menu.add(new MenuInput(this.menu, 0, currentIndex + 1));
		this.menu.handle(new MenuEntryChange(0));
		return this.menu;
	}
	
}
