package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.Position;

public class MenuBuilder {

	private final Collection<Component> components;
	private int currentIndex;
	private final Bus bus;

	public MenuBuilder(Bus bus) {
		this.components = new LinkedList<Component>();
		this.currentIndex = 0;
		this.bus = bus;
	}

	public MenuBuilder addEntry(String text, Message message) {
		UUID entityId = UUID.randomUUID();
		this.addMenuEntry(entityId, text, message);
		return this;
	}
	
	private void addMenuEntry(UUID entityId, String text, Message message) {

		this.components.add(new MenuEntry(this.bus, entityId, this.currentIndex, message));
		this.components.add(new MenuEntryRenderer(this.bus, entityId, this.currentIndex, text));
		this.components.add(new Position(this.bus, entityId));

		this.currentIndex++;

	}

	public Iterable<Component> build() {

		MenuEntryInput menuEntryInput = new MenuEntryInput(this.bus, UUID.randomUUID(), 0, this.currentIndex - 1);
		this.components.add(menuEntryInput);
		this.bus.broadcast(new MenuEntryChange(0));

		return this.components;

	}

}
