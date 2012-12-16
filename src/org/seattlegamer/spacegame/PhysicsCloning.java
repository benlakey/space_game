package org.seattlegamer.spacegame;

import java.util.UUID;

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
