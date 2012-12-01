package org.seattlegamer.spacegame;

import java.util.Stack;

public class StateManager {

	private final Stack<State> states;
	
	public StateManager(State initialState) {
		this.states = new Stack<State>();
		this.loadState(initialState);
	}
	
	public Iterable<Entity> getEntities() {
		State currentState = this.states.peek();
		return currentState.getEntities();
	}
	
	public void loadState(State state) {
		state.load();
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
