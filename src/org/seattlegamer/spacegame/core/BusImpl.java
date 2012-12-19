package org.seattlegamer.spacegame.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

public class BusImpl implements Bus {

	private static final Logger logger = Logger.getLogger(BusImpl.class);

	private final Map<Class<?>, Collection<ReflectiveHandler>> handlers;

	public BusImpl() {
		this.handlers = new HashMap<>();
	}

	@Override
	public void register(Object handler, UUID entityId) {

		Set<Method> handlerMethods = this.getHandlerMethods(handler);
		
		for(Method method : handlerMethods) {
			Class<?> messageClass = method.getParameterTypes()[0];
			Collection<ReflectiveHandler> handlersForClass = this.handlers.get(messageClass);
			if(handlersForClass == null) {
				handlersForClass = new LinkedList<>();
				this.handlers.put(messageClass, handlersForClass);
			}
			handlersForClass.add(new ReflectiveHandler(handler, method, entityId));
		}

	}
	
	@Override
	public void deregister(Object handler, UUID entityId) {
		
		Set<Method> handlerMethods = this.getHandlerMethods(handler);
		
		for(Method method : handlerMethods) {
			Class<?> messageClass = method.getParameterTypes()[0];
			Collection<ReflectiveHandler> handlersForClass = this.handlers.get(messageClass);
			if(handlersForClass == null) {
				continue;
			}
			
			for(ReflectiveHandler reflectiveHandler : handlersForClass) {
				if(reflectiveHandler.getTarget() == handler) {
					reflectiveHandler.disable(); //TODO: this is a hack. actually remove once proper data structures exist here.
				}
			}

		}

	}
	
	private Set<Method> getHandlerMethods(Object obj) {

		if(obj == null) {
			return Collections.emptySet();
		}

		Set<Method> handlerMethods = new HashSet<Method>();

		Class<?> clazz = obj.getClass();
		
		while(clazz != null) {
			for(Method method : clazz.getDeclaredMethods()) {
				if(!Modifier.isPublic(method.getModifiers())) {
					continue;
				}
				if(!method.isAnnotationPresent(Subscription.class)) {
					continue;
				}
				if (method.getParameterTypes().length != 1) {
					continue;
				}
				handlerMethods.add(method);
			}
			clazz = clazz.getSuperclass();
		}

		return handlerMethods;

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

		Collection<ReflectiveHandler> handlers = this.handlers.get(event.getClass());
		if(handlers == null) {
			return;
		}
		for(ReflectiveHandler handler : handlers) {
			if(entityId != null) {
				if(!entityId.equals(handler.getEntityId())) {
					continue;
				}
			}
			handler.handle(event);
		}
		
	}

}
