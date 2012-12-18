package org.seattlegamer.spacegame.core;

public interface Handler<T> {
	void handle(Object message);
	T getTarget();
}
