package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public final class Entity {

	private final Collection<Component> components;
	private final Bus bus;

	public Entity() {
		this.components = new LinkedList<Component>();
		this.bus = new Bus();
	}
	
	public <T extends Message> void register(Class<T> messageClass, Handler<T> handler) {
		this.bus.register(messageClass, handler);
	}
	
	public <T extends Message> void deregister(Class<T> messageClass, Handler<T> handler) {
		this.bus.deregister(messageClass, handler);
	}
	
	public void register(Component component) {
		this.components.add(component);
	}
	
	public void deregister(Component component) {
		this.components.remove(component);
	}

	public <T extends Message> void broadcast(T message) {
		this.bus.broadcast(message);
	}

	public void update(Input input, long elapsedMillis) {
		Iterator<Component> iterator = this.components.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if(component.isEnabled()) {
				component.update(input, elapsedMillis);
			}
		}
	}
	
	public void render(Graphics2D graphics) {
		Iterator<Component> iterator = this.components.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if(component.isEnabled()) {
				component.render(graphics);
			}
		}
	}

}
