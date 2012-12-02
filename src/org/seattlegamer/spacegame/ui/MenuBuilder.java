package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.StateControlInput;

public class MenuBuilder {

	private final Collection<Entity> menuEntries;
	private final int maxIndex;
	private int currentIndex;

	public MenuBuilder(int capacity) {
		if(capacity == 0) {
			throw new IllegalArgumentException("Menu capacity must be greater than 0.");
		}
		this.menuEntries = new LinkedList<Entity>();
		this.maxIndex = capacity - 1;
		this.currentIndex = 0;
	}

	public MenuBuilder addEntry(String text, Message message) {
		
		Entity entry = new Entity();
		
		MenuEntry menuEntry = new MenuEntry(entry, this.currentIndex, message);
		MenuEntryRenderer renderer = new MenuEntryRenderer(entry, this.currentIndex, text);
		
		entry.register(renderer);
		entry.register(menuEntry);
		
		entry.register(new StateControlInput(entry));
		entry.register(new MenuEntryInput(entry, 0, this.maxIndex));
		entry.register(new Position(entry));
		entry.broadcast(new MenuEntryChange(0));

		this.menuEntries.add(entry);
		
		this.currentIndex++;

		return this;

	}
	
	public Iterable<Entity> build() {
		return this.menuEntries;
	}

}
