package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.activities.ComponentBase;

public class ComponentTransition implements Command {

	private ComponentBase newComponent;
	
	public ComponentTransition(ComponentBase newComponent) {
		this.newComponent = newComponent;
	}

	public ComponentBase getNewComponent() {
		return newComponent;
	}
	
}
