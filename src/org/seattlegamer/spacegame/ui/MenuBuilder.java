package org.seattlegamer.spacegame.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ExitStateCommand;
import org.seattlegamer.spacegame.LoadStateCommand;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.Position;
import org.seattlegamer.spacegame.State;
import org.seattlegamer.spacegame.StateControlInput;

public class MenuBuilder {

	private final Collection<Component> components;
	private final Map<UUID, Collection<Component>> entityComponents;
	private int currentIndex;
	private final Bus bus;

	public MenuBuilder(Bus bus) {
		this.components = new LinkedList<Component>();
		this.entityComponents = new HashMap<UUID, Collection<Component>>();
		this.currentIndex = 0;
		this.bus = bus;
	}

	public MenuBuilder addStateLoadEntry(String text, State state) {
		UUID entityId = UUID.randomUUID();
		this.addMenuEntry(entityId, text, new LoadStateCommand(state));
		return this;
	}
	
	public MenuBuilder addStateExitEntry(String text) {
		UUID entityId = UUID.randomUUID();
		this.addMenuEntry(entityId, text, new ExitStateCommand());
		return this;
	}
	
	private void addMenuEntry(UUID entityId, String text, Message message) {
		
		this.addMenuComponent(entityId, new MenuEntry(this.bus, entityId, this.currentIndex, message));
		this.addMenuComponent(entityId, new MenuEntryRenderer(this.bus, entityId, this.currentIndex, text));
		this.addMenuComponent(entityId, new StateControlInput(this.bus, entityId));
		this.addMenuComponent(entityId, new Position(this.bus, entityId));

		this.currentIndex++;

	}
	
	private void addMenuComponent(UUID entityId, Component component) {

		Collection<Component> componentsForEntity = this.entityComponents.get(entityId);
		if(componentsForEntity == null) {
			componentsForEntity = new LinkedList<Component>();
			this.entityComponents.put(entityId, componentsForEntity);
		}

		componentsForEntity.add(component);
		this.components.add(component);

	}
	
	public Iterable<Component> build() {
		
		for(UUID entityId : this.entityComponents.keySet()) {
			MenuEntryInput menuEntryInput = new MenuEntryInput(this.bus, entityId, 0, this.currentIndex);
			this.addMenuComponent(entityId, menuEntryInput);
			this.bus.broadcast(new MenuEntryChange(0));
		}

		return this.components;

	}

}
