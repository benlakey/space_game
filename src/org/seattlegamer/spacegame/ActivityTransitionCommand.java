package org.seattlegamer.spacegame;

public class ActivityTransitionCommand implements Command {

	private Activity newActivity;
	
	public ActivityTransitionCommand(Activity newActivity) {
		this.newActivity = newActivity;
	}

	public Activity getNewActivity() {
		return newActivity;
	}
	
}
