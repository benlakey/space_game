package org.seattlegamer.spacegame.messages;

import org.seattlegamer.spacegame.core.Component;

public class ComponentAddition {

	private final Component component;
	
	public ComponentAddition(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return this.component;
	}
	
}
