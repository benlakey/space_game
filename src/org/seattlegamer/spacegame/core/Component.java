package org.seattlegamer.spacegame.core;

import java.awt.Graphics2D;
import java.util.UUID;

public abstract class Component {

	protected final ComponentBus bus;
	private final UUID entityId;
	private boolean enabled;

	public Component(ComponentBus bus, UUID entityId) {
		this.bus = bus;
		this.entityId = entityId;
		this.setEnabled(true);
	}

	public UUID getEntityId() {
		return this.entityId;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(enabled) {
			this.bus.register(this);
		} else {
			this.bus.deregister(this);
		}
	}

	public void update(Input input, long elapsedMillis) {}

	public void render(Graphics2D graphics) {}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
