package org.seattlegamer.spacegame.core;

import java.awt.Font;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class MenuBuilder {
	
	private static final Font MENU_FONT = new Font("Arial", Font.BOLD, 64);

	private final Bus bus;
	private final Map<String, MenuAction> entries;

	public MenuBuilder(Bus bus) {
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
			components.add(new MenuEntryRenderer(this.bus, entityId, currentIndex, entry.getKey(), MENU_FONT));
			components.add(new MenuEntry(this.bus, entityId, currentIndex, entry.getValue()));
			currentIndex++;
		}
		
		components.add(new MenuInput(this.bus, entityId, currentIndex));

		return components;

	}

}