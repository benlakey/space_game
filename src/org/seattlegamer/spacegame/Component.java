package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.util.UUID;

public abstract class Component {

	protected Bus bus;
	protected UUID entityId;
	private boolean enabled;

	public Component(Bus bus, UUID entityId) {
		this.bus = bus;
		this.entityId = entityId;
		this.enabled = true;
	}
	
	public UUID getEntityId() {
		return this.entityId;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void update(Input input, long elapsedMillis) {
		
	}

	public void render(Graphics2D graphics) {
		
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
