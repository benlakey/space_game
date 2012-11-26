package org.seattlegamer.spacegame;

public class LoadStateCommand implements Message {

	private final State stateToLoad;
	
	public LoadStateCommand(State stateToLoad) {
		this.stateToLoad = stateToLoad;
	}

	public State getStateToLoad() {
		return stateToLoad;
	}

}
