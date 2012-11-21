package org.seattlegamer.spacegame;

import java.util.EnumSet;

public abstract class Component implements Handler {

	protected final Handler owner;
	private boolean enabled;
	private EnumSet<ComponentGroup> groupBitMask;
	
	public Component(Handler owner) {
		this.owner = owner;
		this.enabled = true;
		this.groupBitMask = EnumSet.noneOf(ComponentGroup.class);
	}
	
	public void applyGroup(ComponentGroup group) {
		this.groupBitMask.add(group);
    }

    public void removeGroup(ComponentGroup group) {
    	this.groupBitMask.remove(group);
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
	
	@Override
	public void handle(Message message) {

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
