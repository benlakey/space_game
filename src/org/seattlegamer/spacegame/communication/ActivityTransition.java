package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.activities.Activity;

public class ActivityTransition implements Command {

	private Activity newActivity;
	
	public ActivityTransition(Activity newActivity) {
		this.newActivity = newActivity;
	}

	public Activity getNewActivity() {
		return newActivity;
	}
	
}
