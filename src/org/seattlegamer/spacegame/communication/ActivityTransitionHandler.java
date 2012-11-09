package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.Engine;
import org.seattlegamer.spacegame.activities.Activity;

public class ActivityTransitionHandler implements Handler {
	
	private final Engine engine;

	public ActivityTransitionHandler(Engine engine) {
		this.engine = engine;
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ActivityTransition;
	}

	@Override
	public void handle(Command command) {
		ActivityTransition transition = (ActivityTransition)command;
		Activity newActivity = transition.getNewActivity();
		this.engine.setActivity(newActivity);
	}

}
