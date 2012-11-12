package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.components.ComponentBase;

public class ComponentTransition implements Command {

	private final ComponentBase newComponent;
	
	public ComponentTransition(ComponentBase newComponent) {
		this.newComponent = newComponent;
	}

	public ComponentBase getNewComponent() {
		return newComponent;
	}
	
}
