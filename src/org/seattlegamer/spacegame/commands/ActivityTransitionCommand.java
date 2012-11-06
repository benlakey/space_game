package org.seattlegamer.spacegame.commands;

import org.seattlegamer.spacegame.activities.Activity;

public class ActivityTransitionCommand implements Command {

	private Activity newActivity;
	
	public ActivityTransitionCommand(Activity newActivity) {
		this.newActivity = newActivity;
	}

	public Activity getNewActivity() {
		return newActivity;
	}
	
}
