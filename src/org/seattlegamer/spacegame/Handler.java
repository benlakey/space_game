package org.seattlegamer.spacegame;

import java.util.UUID;

public interface Handler<T> {
	boolean canHandleFrom(UUID sourceEntityId);
	void handle(T message);
}
