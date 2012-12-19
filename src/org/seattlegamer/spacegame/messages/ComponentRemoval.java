package org.seattlegamer.spacegame.messages;

import org.seattlegamer.spacegame.core.Component;

public class ComponentRemoval {

	private final Component component;
	
	public ComponentRemoval(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return this.component;
	}
	
}
