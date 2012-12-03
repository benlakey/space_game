package org.seattlegamer.spacegame;

import java.io.IOException;
import java.util.Stack;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class StateManager {

	private static Logger logger = Logger.getLogger(StateManager.class);
	
	private final Bus bus;
	private final ResourceCache resourceCache;
	private final Stack<State> states;
	
	public StateManager(Bus bus, ResourceCache resourceCache, State initialState) {

		this.bus = bus;
		this.resourceCache = resourceCache;
		this.states = new Stack<State>();
		
		this.bus.register(LoadStateCommand.class, this.getLoadStateCommandHandler());
		this.bus.register(ExitStateCommand.class, this.getExitStateCommandHandler());
		
		this.loadState(initialState);

	}
	
	public Iterable<Component> getComponents() {
		State currentState = this.states.peek();
		return currentState.getComponents();
	}
	
	public void loadState(State state) {

		try {
			state.load(this.bus, this.resourceCache);
		} catch (IOException e) {
			logger.fatal("State failed to load.", e);
			System.exit(-1);
		}
		
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

			@Override
			public UUID getEntityIdHandlingFor() {
				return null;
			}

		};
	}
	
	private Handler<ExitStateCommand> getExitStateCommandHandler() {
		return new Handler<ExitStateCommand>() {

			@Override
			public void handle(ExitStateCommand message) {
				StateManager.this.exitState();
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return null;
			}

		};
	}

}
