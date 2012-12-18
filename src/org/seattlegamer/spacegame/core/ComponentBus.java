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

public class ComponentBus implements Bus<Component> {

	private static final Logger logger = Logger.getLogger(ComponentBus.class);

	private final Map<Class<?>, Collection<ReflectiveHandler<Component>>> handlers;

	public ComponentBus() {
		this.handlers = new HashMap<>();
	}

	@Override
	public void register(Component obj) {
		this.modifyHandlers(obj, false);
	}

	@Override
	public void deregister(Component obj) {
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
		
		Collection<ReflectiveHandler<Component>> handlersForClass = this.handlers.get(messageClass);
		if(handlersForClass == null) {
			handlersForClass = new LinkedList<>();
			this.handlers.put(messageClass, handlersForClass);
		}
		
		ReflectiveHandler<Component> handler = new ReflectiveHandler<Component>(target, method);
		handlersForClass.add(handler);
		
	}

	private void removeHandler(Class<?> messageClass, Object obj) {
		
		Collection<ReflectiveHandler<Component>> handlersForClass = this.handlers.get(messageClass);
		if(handlersForClass == null) {
			return;
		}

		Iterator<ReflectiveHandler<Component>> iterator = handlersForClass.iterator();
		while(iterator.hasNext()) {
			ReflectiveHandler<Component> handler = iterator.next();
			Object target = handler.getTarget();
			if(target == obj) {
				iterator.remove();
			}
		}
		
	}

	@Override
	public void broadcast(Object event) {
		this.send(event, null);
	}

	@Override
	public void send(Object event, UUID entityId) {
		
		if(entityId == null) {
			logger.debug("broadcasting " + event);
		} else { 
			logger.debug("sending " + event + " to " + entityId);
		}

		Collection<ReflectiveHandler<Component>> handlers = this.handlers.get(event.getClass());
		if(handlers == null) {
			return;
		}
		for(ReflectiveHandler<Component> handler : handlers) {
			if(entityId != null) {
				Component target = handler.getTarget();
				if(!entityId.equals(target.getEntityId())) {
					continue;
				}
			}
			handler.handle(event);
		}
		
	}

}
