package org.seattlegamer.spacegame;

import java.util.UUID;

public abstract class Message {
	
	private final UUID sourceEntityId;

	protected Message(UUID sourceEntityId) {
		this.sourceEntityId = sourceEntityId;
	}
	
	public UUID getSourceEntityId() {
		return this.sourceEntityId;
	}

}
