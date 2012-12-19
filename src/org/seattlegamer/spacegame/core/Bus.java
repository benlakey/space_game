package org.seattlegamer.spacegame.core;

import java.util.UUID;

public interface Bus {
	void register(Object handler, UUID entityId);
	void deregister(Object handler, UUID entityId);
	void broadcast(Object event);
	void send(Object event, UUID entityId);
}
