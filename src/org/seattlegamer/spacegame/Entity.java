package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings("rawtypes")
public final class Entity {

	private final Collection<Component> components;
	private final Map<Class<? extends Message>, Collection<Handler>> handlerRegistry;

	public Entity() {
		this.components = new LinkedList<Component>();
		this.handlerRegistry = new HashMap<Class<? extends Message>, Collection<Handler>>();
	}
	
	public <T extends Message> void registerHandler(Class<T> messageClass, Handler handler) {
		Collection<Handler> handlers = this.handlerRegistry.get(messageClass);
		if(handlers == null) {
			handlers = new LinkedList<Handler>();
			this.handlerRegistry.put(messageClass, handlers);
		}
		handlers.add(handler);
	}

	@SuppressWarnings("unchecked")
	public <T> void broadcast(Class<T> messageClass, Message message) {
		Collection<Handler> handlers = this.handlerRegistry.get(messageClass);
		if(handlers == null) {
			return;
		}
		for(Handler handler : handlers) {
			handler.handle(message);
		}
	}

	public void add(Component component) {
		this.components.add(component);
	}

	public void update(Input input, long elapsedMillis) {
		for(Component component : this.components) {
			if(component.isEnabled()) {
				component.update(input, elapsedMillis);
			}
		}
	}
	
	public void render(Graphics2D graphics) {
		for(Component component : this.components) {
			if(component.isEnabled()) {
				component.render(graphics);
			}
		}
	}

}
