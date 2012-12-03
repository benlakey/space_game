package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class Bus {

	private final Map<Class<? extends Message>, Collection<Handler<? extends Message>>> handlers;
	
	public Bus() {
		this.handlers = new HashMap<>();
	}
	
	public <T extends Message> void register(Class<T> messageClass, Handler<T> handler) {

		Collection<Handler<? extends Message>> handlersForMessageType = this.handlers.get(messageClass);
		if(handlersForMessageType == null) {
			handlersForMessageType = new LinkedList<>();
			this.handlers.put(messageClass, handlersForMessageType);
		}

		handlersForMessageType.add(handler);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Message> void broadcast(T message) {
		
		Collection<Handler<? extends Message>> handlersForMessageType = this.handlers.get(message.getClass());
		if(handlersForMessageType == null) {
			return;
		}
		
		for(Handler handler : handlersForMessageType) {
			UUID sourceEntityId = message.getSourceEntityId();
			if(handler.canHandleFrom(sourceEntityId)) {
				handler.handle(message);
			}
		}
		
	}


	
}
