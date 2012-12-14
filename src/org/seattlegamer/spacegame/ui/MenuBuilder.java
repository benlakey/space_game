package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;

public class MenuBuilder {

	private final ComponentBus bus;
	private final Map<String, MenuAction> entries;

	public MenuBuilder(ComponentBus bus) {
		this.bus = bus;
		this.entries = new LinkedHashMap<String, MenuAction>();
	}

	public MenuBuilder addMenuEntry(String text, MenuAction action) {
		this.entries.put(text, action);
		return this;
	}

	public Iterable<Component> build(UUID entityId) {

		Collection<Component> components = new LinkedList<Component>();
		
		int currentIndex = 0;
		
		for(Map.Entry<String, MenuAction> entry : entries.entrySet()) {
			components.add(new MenuEntryRenderer(this.bus, entityId, currentIndex, entry.getKey()));
			components.add(new MenuEntry(this.bus, entityId, currentIndex, entry.getValue()));
			currentIndex++;
		}
		
		components.add(new MenuInput(this.bus, entityId, currentIndex));

		return components;

	}

}