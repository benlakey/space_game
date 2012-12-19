package org.seattlegamer.spacegame.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.seattlegamer.spacegame.messages.ComponentAddition;
import org.seattlegamer.spacegame.messages.ComponentRemoval;
import org.seattlegamer.spacegame.resources.ResourceCache;

public abstract class State {

	protected final Bus bus;
	protected final ResourceCache resourceCache;
	
	private final Collection<Component> components;
	
	protected final Queue<Component> componentsToAdd;
	protected final Queue<Component> componentsToRemove;
	
	public State(Bus bus, ResourceCache resourceCache) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.components = new LinkedList<Component>();
		this.componentsToAdd = new LinkedList<Component>();
		this.componentsToRemove = new LinkedList<Component>();
	}

	public void onActivate() {
		this.bus.register(this, null);
		for(Component component : this.components) {
			this.bus.register(component, component.getEntityId());
		}
	}

	public void onDeactivate() {
		this.bus.deregister(this, null);
		for(Component component : this.components) {
			this.bus.deregister(component, component.getEntityId());
		}
	}

	@Subscription
	public void addComponent(ComponentAddition addition) {
		Component toAdd = addition.getComponent();
		this.componentsToAdd.offer(toAdd);
		this.bus.register(toAdd, toAdd.getEntityId());
	}
	
	@Subscription
	public void removeComponent(ComponentRemoval removal) {
		Component toRemove = removal.getComponent();
		this.componentsToRemove.offer(toRemove);
		this.bus.deregister(toRemove, toRemove.getEntityId());
	}

	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		
		for(Component component : this.components) {
			component.update(keyInput, pointerInput, elapsedMillis);
		}
		
		while(!this.componentsToRemove.isEmpty()) {
			Component toRemove = this.componentsToRemove.poll();
			this.components.remove(toRemove);
		}
		
		while(!this.componentsToAdd.isEmpty()) {
			Component toAdd = this.componentsToAdd.poll();
			this.components.add(toAdd);
		}
		
	}

	public void render(Renderer renderer) {
		renderer.render(components);
	}
}
