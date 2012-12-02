package org.seattlegamer.spacegame;

import java.io.IOException;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class StateManager {

	private static Logger logger = Logger.getLogger(StateManager.class);
	
	private final ResourceCache resourceCache;
	private final Stack<State> states;
	
	public StateManager(ResourceCache resourceCache, State initialState) {
		this.resourceCache = resourceCache;
		this.states = new Stack<State>();
		this.loadState(initialState);
	}
	
	public Iterable<Entity> getEntities() {
		State currentState = this.states.peek();
		return currentState.getEntities();
	}
	
	public void loadState(State state) {
		try {
			state.load(this.resourceCache);
		} catch (IOException e) {
			logger.fatal("State failed to load.", e);
			System.exit(-1);
		}
		state.applyGlobalHandler(LoadStateCommand.class, this.getLoadStateCommandHandler());
		state.applyGlobalHandler(ExitStateCommand.class, this.getExitStateCommandHandler());
		this.states.push(state);
	}
	
	public void exitState() {
		if(this.states.size() <= 1) {
			System.exit(0);
		}
		this.states.pop();
	}

	private Handler<LoadStateCommand> getLoadStateCommandHandler() {
		return new Handler<LoadStateCommand>() {
			@Override
			public void handle(LoadStateCommand message) {
				State stateToLoad = message.getStateToLoad();
				StateManager.this.loadState(stateToLoad);
			}
		};
	}
	
	private Handler<ExitStateCommand> getExitStateCommandHandler() {
		return new Handler<ExitStateCommand>() {
			@Override
			public void handle(ExitStateCommand message) {
				StateManager.this.exitState();
			}
		};
	}

}
