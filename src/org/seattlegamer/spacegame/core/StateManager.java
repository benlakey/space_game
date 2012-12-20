package org.seattlegamer.spacegame.core;

import java.util.HashMap;
import java.util.Map;

import org.seattlegamer.spacegame.messages.StateChange;
import org.seattlegamer.spacegame.utils.Throttle;

public class StateManager {

	private static final int STATE_TOGGLE_DELAY_MILLIS = 300;

	private final Map<Class<? extends State>, State> states;
	private State currentState;
	private final Throttle stateToggleThrottle;

	public StateManager(Iterable<State> allStates) {
		this.states = new HashMap<>();
		for(State state : allStates) {
			this.states.put(state.getClass(), state);
		}
		this.stateToggleThrottle = new Throttle(STATE_TOGGLE_DELAY_MILLIS);
	}

	@Subscription
	public void changeState(StateChange stateChange) {
		
		Class<? extends State> stateClass = stateChange.getNewStateClass();
		
		if(stateClass == null) {
			return;
		}
		
		if(this.stateToggleThrottle.getMillisUntilExecution() > 0) {
			return;
		}

		State newState = this.states.get(stateClass);
		if(newState == null) {
			return;
		}

		if(this.currentState != null) {
			this.currentState.onDeactivate();
		}
		this.currentState = newState;
		this.currentState.onActivate();

	}

	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		this.stateToggleThrottle.tick(elapsedMillis);
		this.currentState.update(keyInput, pointerInput, elapsedMillis);
	}
	
	public void render(Renderer renderer) {
		this.currentState.render(renderer);
	}
	
}
