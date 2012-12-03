package org.seattlegamer.spacegame;

import java.util.UUID;

public interface Handler<T> {
	UUID getEntityIdHandlingFor();
	void handle(T message);
}
