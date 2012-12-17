package org.seattlegamer.spacegame.messages;

import java.util.UUID;

import org.seattlegamer.spacegame.core.Physics;

public class PhysicsCloning {

	private final UUID entityIdFrom;
	private final Physics toCloneTo;
	
	public PhysicsCloning(UUID entityIdFrom, Physics toCloneTo) {
		this.entityIdFrom = entityIdFrom;
		this.toCloneTo = toCloneTo;
	}
	
	public UUID getEntityIdFrom() {
		return this.entityIdFrom;
	}

	public Physics getToCloneTo() {
		return this.toCloneTo;
	}

}
