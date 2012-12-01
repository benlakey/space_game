package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.LinkedList;

public abstract class State {

	protected final Collection<Entity> entities;

	public State() {
		this.entities = new LinkedList<Entity>();
	}

	public abstract void load();
	
	public Iterable<Entity> getEntities() {
		return this.entities;
	}
	
	@SuppressWarnings("rawtypes")
	public <T extends Message> void applyGlobalHandler(Class<T> messageClass, Handler handler) {
		for(Entity entity : this.entities) {
			entity.registerHandler(messageClass, handler);
		}
	}
	
	protected void registerEntity(Entity entity) {
		this.entities.add(entity);
	}

}
