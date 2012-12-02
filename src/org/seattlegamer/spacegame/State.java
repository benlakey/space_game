package org.seattlegamer.spacegame;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.resources.ResourceCache;

public abstract class State {

	protected final Collection<Entity> entities;

	public State() {
		this.entities = new LinkedList<Entity>();
	}

	public abstract void load(ResourceCache resourceCache) throws IOException;
	
	public Iterable<Entity> getEntities() {
		return this.entities;
	}
	
	@SuppressWarnings("rawtypes")
	public <T extends Message> void applyGlobalHandler(Class<T> messageClass, Handler handler) {
		for(Entity entity : this.entities) {
			entity.register(messageClass, handler);
		}
	}
	
	protected void registerEntity(Entity entity) {
		this.entities.add(entity);
	}

}
