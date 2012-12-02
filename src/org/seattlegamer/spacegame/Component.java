package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.util.EnumSet;

public abstract class Component {

	protected final Entity entity;
	private boolean enabled;
	private final EnumSet<ComponentGroup> groupBitMask;

	public Component(Entity entity) {
		this.entity = entity;
		this.enabled = true;
		this.groupBitMask = EnumSet.noneOf(ComponentGroup.class);
	}

	public void setGroupMembership(ComponentGroup group, boolean isMember) {
		if(isMember) {
			this.groupBitMask.add(group);
		} else {
			this.groupBitMask.remove(group);
		}
    }

	public boolean isMember(ComponentGroup group) {
		return this.groupBitMask.contains(group);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void update(Input input, long elapsedMillis) {
		
	}
	
	public void render(Graphics2D graphics, boolean screenSizeChanged) {
		
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
