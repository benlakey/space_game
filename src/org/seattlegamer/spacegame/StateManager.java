package org.seattlegamer.spacegame;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.utils.Throttle;

public class StateManager {
	
	private static final int STATE_TOGGLE_DELAY_MILLIS = 300;

	private static final Logger logger = Logger.getLogger(StateManager.class);

	private final ComponentBus bus;
	private final ResourceCache resourceCache;
	private State currentState;
	private final Throttle stateToggleThrottle;

	public StateManager(ComponentBus bus, ResourceCache resourceCache, State initialState) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.changeState(initialState);
		this.stateToggleThrottle = new Throttle(STATE_TOGGLE_DELAY_MILLIS);
	}

	public void changeState(State state) {
		if(state == null) {
			return;
		}
		//TODO: switch in a 'loading' state and perform state loading on a 
		//different thread. when it finishes, swap in the right state in place 
		//of the 'loading' state. (or something similar)
		try {
			state.load(this.bus, this.resourceCache, this);
		} catch (IOException e) {
			logger.fatal("State '" + state + "' could not load!", e);
			throw new RuntimeException(e);
		}
		this.currentState = state;
	}

	public void update(Input input, long elapsedMillis) {
		currentState.update(input, elapsedMillis);
	}
	
	public void render(Renderer renderer) {
		currentState.render(renderer);
	}

	public Throttle getStateToggleThrottle() {
		return this.stateToggleThrottle;
	}
	
}
