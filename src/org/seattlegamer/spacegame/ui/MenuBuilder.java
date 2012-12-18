package org.seattlegamer.spacegame.ui;

import java.awt.Font;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.StateManager;

public class MenuBuilder {
	
	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 64);

	private final Bus<Component> bus;
	private final Map<String, MenuAction> entries;
	private Font font;

	public MenuBuilder(Bus<Component> bus) {
		this.bus = bus;
		this.entries = new LinkedHashMap<String, MenuAction>();
		this.font = DEFAULT_FONT;
	}

	public MenuBuilder addMenuEntry(String text, MenuAction action) {
		this.entries.put(text, action);
		return this;
	}
	
	public MenuBuilder setFont(Font font) {
		this.font = font;
		return this;
	}

	public Iterable<Component> build(UUID entityId, StateManager stateManager) {

		Collection<Component> components = new LinkedList<Component>();

		int currentIndex = 0;
		
		for(Map.Entry<String, MenuAction> entry : entries.entrySet()) {
			components.add(new MenuEntryRenderer(this.bus, entityId, currentIndex, entry.getKey(), this.font));
			components.add(new MenuEntry(this.bus, entityId, currentIndex, entry.getValue()));
			currentIndex++;
		}
		
		components.add(new MenuInput(this.bus, entityId, stateManager, currentIndex));

		return components;

	}

}