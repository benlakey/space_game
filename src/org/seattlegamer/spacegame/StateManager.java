package org.seattlegamer.spacegame;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class StateManager {
	
	private static final Logger logger = Logger.getLogger(StateManager.class);
	
	private static StateManager instance;
	
	public static StateManager from(ComponentBus bus, ResourceCache resourceCache, State initialState) {
		instance = new StateManager(bus, resourceCache, initialState);
		return instance;
	}
	
	public static void setState(State state) {
		instance.changeState(state);
	}
	
	private final ComponentBus bus;
	private final ResourceCache resourceCache;
	private State currentState;

	private StateManager(ComponentBus bus, ResourceCache resourceCache, State initialState) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.changeState(initialState);
	}

	public void changeState(State state) {
		if(state == null) {
			return;
		}
		//TODO: switch in a 'loading' state and perform state loading on a 
		//different thread. when it finishes, swap in the right state in place 
		//of the 'loading' state. (or something similar)
		try {
			state.load(this.bus, this.resourceCache);
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
	
}
