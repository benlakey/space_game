package org.seattlegamer.spacegame.core;

import java.awt.Graphics2D;
import java.util.UUID;

public abstract class Component {

	protected final Bus bus;
	private final UUID entityId;

	public Component(Bus bus, UUID entityId) {
		this.bus = bus;
		this.entityId = entityId;
	}

	public UUID getEntityId() {
		return this.entityId;
	}

	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {}

	public void render(Graphics2D graphics) {}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
