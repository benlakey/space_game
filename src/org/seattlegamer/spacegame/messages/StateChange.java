package org.seattlegamer.spacegame.messages;

import org.seattlegamer.spacegame.core.State;

public class StateChange {

	private final Class<? extends State> newStateClass;
	
	public StateChange(Class<? extends State> newStateClass) {
		this.newStateClass = newStateClass;
	}

	public Class<? extends State> getNewStateClass() {
		return this.newStateClass;
	}
	
}
