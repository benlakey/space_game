package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class State {

	protected final Bus bus;
	protected final Collection<Component> components;
	protected final Collection<ActivationCommand> activationCommands;
	protected final Collection<ActivationCommand> deactivationCommands;

	public State(Bus bus) {
		this.bus = bus;
		this.components = new LinkedList<Component>();
		this.activationCommands = new LinkedList<ActivationCommand>();
		this.deactivationCommands = new LinkedList<ActivationCommand>();
	}
	
	public Iterable<Component> getComponents() {
		return this.components;
	}
	
	public void activate() {
		Iterator<Component> componentIterator = this.components.iterator();
		while (componentIterator.hasNext()) {
			Component component = componentIterator.next();
			component.registerHandlers();
		}
		for(ActivationCommand activationCommand : this.activationCommands) {
			activationCommand.execute();
		}
	}
	
	public void deactivate() {
		Iterator<Component> componentIterator = this.components.iterator();
		while (componentIterator.hasNext()) {
			Component component = componentIterator.next();
			component.deregisterHandlers();
		}
		for(ActivationCommand deactivationCommand : this.deactivationCommands) {
			deactivationCommand.execute();
		}
	}

	
	public interface ActivationCommand {
		void execute();
	}

}
