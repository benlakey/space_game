package org.seattlegamer.spacegame.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.utils.Throttle;

public class StateManager {
	
	private static final Logger logger = Logger.getLogger(StateManager.class);
	
	private static final int STATE_TOGGLE_DELAY_MILLIS = 300;

	private final ResourceCache resourceCache;
	private final GameSettings settings;
	private State currentState;
	private final Throttle stateToggleThrottle;
	
	private final Queue<Component> componentsToAdd;
	private final Queue<Component> componentsToRemove;

	public StateManager(ResourceCache resourceCache, GameSettings settings, State initialState) {
		this.resourceCache = resourceCache;
		this.settings = settings;
		this.changeState(initialState);
		this.stateToggleThrottle = new Throttle(STATE_TOGGLE_DELAY_MILLIS);
		this.componentsToAdd = new LinkedList<>();
		this.componentsToRemove = new LinkedList<>();
	}

	public void changeState(State state) {
		if(state == null) {
			return;
		}
		//TODO: interim loading screen state?
		try {
			state.load(this.resourceCache, this, settings);
		} catch (IOException e) {
			logger.fatal("State '" + state + "' could not load!", e);
			throw new RuntimeException(e);
		}
		this.currentState = state;
	}
	
	public void addComponent(Component component) {
		this.componentsToAdd.offer(component);
	}
	
	public void removeComponent(Component component) {
		this.componentsToRemove.offer(component);
	}

	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		currentState.update(keyInput, pointerInput, elapsedMillis);
		while(!this.componentsToAdd.isEmpty()) {
			Component newComponent = this.componentsToAdd.poll();
			this.currentState.addComponent(newComponent);
		}
		while(!this.componentsToRemove.isEmpty()) {
			Component componentToRemove = this.componentsToRemove.poll();
			this.currentState.removeComponent(componentToRemove);
		}
	}
	
	public void render(Renderer renderer) {
		currentState.render(renderer);
	}

	public Throttle getStateToggleThrottle() {
		return this.stateToggleThrottle;
	}
	
}
