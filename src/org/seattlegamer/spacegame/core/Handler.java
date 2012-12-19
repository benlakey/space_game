package org.seattlegamer.spacegame.core;

public interface Handler {
	void handle(Object message);
	Object getTarget();
}
