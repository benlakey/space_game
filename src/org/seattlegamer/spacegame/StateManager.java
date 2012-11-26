package org.seattlegamer.spacegame;

import java.util.Stack;

public class StateManager implements Handler {

	private final Stack<State> states;
	
	public StateManager(State startState) {
		this.states = new Stack<State>();
		startState.load(this);
		this.states.push(startState);
	}
	
	public Iterable<Entity> getEntities() {
		State currentState = this.states.peek();
		return currentState.getEntities();
	}
	
	@Override
	public void handle(Message message) {
		if(message instanceof LoadStateCommand) {
			LoadStateCommand loadStateCommand = (LoadStateCommand)message;
			State stateToLoad = loadStateCommand.getStateToLoad();
			this.loadState(stateToLoad);
		} else if(message instanceof ExitStateCommand) {
			this.exitCurrentState();
		}
	}
	
	public void loadState(State state) {
		state.load(this);
		this.states.push(state);
	}
	
	public void exitCurrentState() {
		if(this.states.size() == 1) {
			System.exit(0);
		}
		this.states.pop();
	}

}
