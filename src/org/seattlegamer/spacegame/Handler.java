package org.seattlegamer.spacegame;

import java.lang.reflect.Method;

public class Handler {

	private final Component target;
	private final Method method;

	public Handler(Component target, Method method) {
		this.target = target;
		this.method = method;
	}

	public void handle(Object message) throws ReflectiveOperationException, IllegalArgumentException {
		method.invoke(getTarget(), new Object[] { message });
	}

	public Component getTarget() {
		return target;
	}

}
