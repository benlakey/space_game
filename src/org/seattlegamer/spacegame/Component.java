package org.seattlegamer.spacegame;

import java.util.HashSet;
import java.util.Set;

public abstract class Component implements Handler {

	protected final Handler owner;
	private boolean enabled;
	private Set<ComponentGroup> groups;
	
	public Component(Handler owner) {
		this.owner = owner;
		this.enabled = true;
		this.groups = new HashSet<ComponentGroup>();
	}

	public void applyGroup(ComponentGroup group) {
		this.groups.add(group);
	}
	
	public boolean isMember(ComponentGroup group) {
		return this.groups.contains(group);
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
