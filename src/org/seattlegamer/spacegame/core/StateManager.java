package org.seattlegamer.spacegame.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.seattlegamer.spacegame.messages.StateChange;

public class StateManager {

	private static final int STATE_TOGGLE_DELAY_MILLIS = 300;

	private boolean toggleReady;
	private final Timer toggleThrottle;
	private final Map<Class<? extends State>, State> states;
	private State currentState;

	public StateManager(Iterable<State> allStates) {
		this.toggleReady = true;
		this.toggleThrottle = new Timer();
		this.states = new HashMap<>();
		for(State state : allStates) {
			this.states.put(state.getClass(), state);
		}
	}

	@Subscription
	public void changeState(StateChange stateChange) {
		
		if(!this.toggleReady) {
			return;
		}
		
		Class<? extends State> stateClass = stateChange.getNewStateClass();
		
		if(stateClass == null) {
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
		this.throttleStateToggle();

	}
	
	private void throttleStateToggle() {
		this.toggleReady = false;
		this.toggleThrottle.schedule(new TimerTask() {
			@Override
			public void run() {
				toggleReady = true;
			}
		}, STATE_TOGGLE_DELAY_MILLIS);
	}

	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		this.currentState.update(keyInput, pointerInput, elapsedMillis);
	}
	
	public void render(Renderer renderer) {
		this.currentState.render(renderer);
	}
	
}
