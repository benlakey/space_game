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
		MenuEntry menuEntry = new MenuEntry(this.menu, currentIndex, text, message, currentIndex == 0);
		this.menu.add(new MenuEntryRenderer(this.menu, menuEntry.getId()));
		this.menu.add(menuEntry);
		menuEntry.advertiseSelf();
		this.currentIndex++;
		return this;
	}
	
	public Entity build() {
		this.menu.add(new MenuInput(this.menu, 0, currentIndex + 1));
		return this.menu;
	}
	
}
