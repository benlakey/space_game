package org.seattlegamer.spacegame;

import java.util.UUID;

public class LoadStateCommand extends Message {

	private final State stateToLoad;
	
	public LoadStateCommand(UUID sourceEntityId, State stateToLoad) {
		super(sourceEntityId);
		this.stateToLoad = stateToLoad;
	}

	public State getStateToLoad() {
		return stateToLoad;
	}

}
