package org.seattlegamer.spacegame.core;

import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.log4j.Logger;

public class ReflectiveHandler implements Handler {

	private static final Logger logger = Logger.getLogger(ReflectiveHandler.class);
	
	private final Object target;
	private final Method method;
	private final UUID entityId;
	private boolean enabled;

	public ReflectiveHandler(Object target, Method method, UUID entityId) {
		this.target = target;
		this.method = method;
		this.entityId = entityId;
		this.enabled = true;
	}
	
	public void disable() {
		this.enabled = false;
	}

	public void handle(Object message) {
		if(!this.enabled){ 
			return;
		}
		try {
			method.invoke(this.target, new Object[] { message });
		} catch(ReflectiveOperationException e) {
			logger.error(String.format("Unable to invoke handler '%s' for message '%s'", this, message), e);
		} catch(IllegalArgumentException e) {
			logger.error(String.format("Handler '%s' does not accept as a parameter message '%s'", this, message), e);
		}
	}

	public Object getTarget() {
		return this.target;
	}

	public UUID getEntityId() {
		return this.entityId;
	}

}
