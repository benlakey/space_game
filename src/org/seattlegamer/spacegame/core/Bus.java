package org.seattlegamer.spacegame.core;

import java.util.UUID;

public interface Bus<T> {
	void register(T obj);
	void deregister(T obj);
	void broadcast(Object event);
	void send(Object event, UUID entityId);
}
