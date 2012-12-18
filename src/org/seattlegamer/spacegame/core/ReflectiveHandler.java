package org.seattlegamer.spacegame.core;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class ReflectiveHandler<T> implements Handler<T> {

	private static final Logger logger = Logger.getLogger(ReflectiveHandler.class);
	
	private final T target;
	private final Method method;

	public ReflectiveHandler(T target, Method method) {
		this.target = target;
		this.method = method;
	}

	public void handle(Object message) {
		try {
			method.invoke(getTarget(), new Object[] { message });
		} catch(ReflectiveOperationException e) {
			logger.error(String.format("Unable to invoke handler '%s' for message '%s'", this, message));
		} catch(IllegalArgumentException e) {
			logger.error(String.format("Handler '%s' does not accept as a parameter message '%s'", this, message));
		}
	}

	public T getTarget() {
		return target;
	}

}
