package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings("rawtypes")
public final class Bus {

	private final Map<Class<? extends Message>, Collection<Handler>> handlerRegistry;

	public Bus() {
		this.handlerRegistry = new HashMap<Class<? extends Message>, Collection<Handler>>();
	}
	
	public <T extends Message> void register(Class<T> messageClass, Handler handler) {
		Collection<Handler> handlers = this.handlerRegistry.get(messageClass);
		if(handlers == null) {
			handlers = new LinkedList<Handler>();
			this.handlerRegistry.put(messageClass, handlers);
		}
		handlers.add(handler);
	}
	
	public <T extends Message> void deregister(Class<T> messageClass, Handler handler) {
		Collection<Handler> handlers = this.handlerRegistry.get(messageClass);
		if(handlers == null) {
			return;
		}
		handlers.remove(handler);
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

}
