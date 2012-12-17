package org.seattlegamer.spacegame.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

public class ComponentBus {

	private static final Logger logger = Logger.getLogger(ComponentBus.class);

	private final Map<Class<?>, Collection<Handler>> handlers;

	public ComponentBus() {
		this.handlers = new HashMap<>();
	}

	public <T extends Component> void register(T obj) {
		this.modifyHandlers(obj, false);
	}

	public <T extends Component> void deregister(T obj) {
		this.modifyHandlers(obj, true);
	}

	private void modifyHandlers(Component obj, boolean remove) {

		Class<?> clazz = obj.getClass();
		//TODO: make it work for subclasses
		
		for(Method method : clazz.getMethods()) {
			
			if(!Modifier.isPublic(method.getModifiers())) {
				continue;
			}

			if(!method.isAnnotationPresent(Subscription.class)) {
				continue;
			}

			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length != 1) {
				continue;
			}
			
			Class<?> messageClass = parameterTypes[0];
			if(remove) {
				this.removeHandler(messageClass, obj);
			} else {
				this.addHandler(messageClass, obj, method);
			}

		}
		
	}

	private void addHandler(Class<?> messageClass, Component target, Method method) {
		
		Collection<Handler> handlersForClass = this.handlers.get(messageClass);
		if(handlersForClass == null) {
			handlersForClass = new LinkedList<Handler>();
			this.handlers.put(messageClass, handlersForClass);
		}
		
		Handler handler = new Handler(target, method);
		handlersForClass.add(handler);
		
	}

	private void removeHandler(Class<?> messageClass, Object obj) {
		
		Collection<Handler> handlersForClass = this.handlers.get(messageClass);
		if(handlersForClass == null) {
			return;
		}

		Iterator<Handler> iterator = handlersForClass.iterator();
		while(iterator.hasNext()) {
			Handler handler = iterator.next();
			Object target = handler.getTarget();
			if(target == obj) {
				iterator.remove();
			}
		}
		
	}

	public void broadcast(Object event) {
		this.send(event, null);
	}

	public void send(Object event, UUID entityId) {
		
		if(entityId == null) {
			logger.debug("broadcasting " + event);
		} else { 
			logger.debug("sending " + event + " to " + entityId);
		}

		Collection<Handler> handlers = this.handlers.get(event.getClass());
		if(handlers == null) {
			return;
		}
		for(Handler handler : handlers) {
			if(entityId != null) {
				Component target = handler.getTarget();
				if(!entityId.equals(target.getEntityId())) {
					continue;
				}
			}
			try {
				handler.handle(event);
			} catch (IllegalArgumentException e) {
				logger.error("The handler doesn't accept the parameter: " + event);
				continue;
			} catch (ReflectiveOperationException e) {
				logger.error(String.format("Executing the handler '%s' failed for the event '%s'.", handler, event), e);
				continue;
			}
		}
		
	}

}
