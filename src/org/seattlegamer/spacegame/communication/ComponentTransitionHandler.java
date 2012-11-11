package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.Engine;
import org.seattlegamer.spacegame.components.ComponentBase;

public class ComponentTransitionHandler implements Handler {
	
	private final Engine engine;

	public ComponentTransitionHandler(Engine engine) {
		this.engine = engine;
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ComponentTransition;
	}

	@Override
	public void handle(Command command) {
		ComponentTransition transition = (ComponentTransition)command;
		ComponentBase newComponent = transition.getNewComponent();
		this.engine.setComponent(newComponent);
	}

}
