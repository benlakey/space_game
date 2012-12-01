package org.seattlegamer.spacegame;

public interface Handler<T> {
	void handle(T message);
}
