package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.LinkedList;

public abstract class State {

	protected final Bus bus;
	protected final Collection<Component> components;

	public State(Bus bus) {
		this.bus = bus;
		this.components = new LinkedList<Component>();
	}
	
	public Iterable<Component> getComponents() {
		return this.components;
	}

}
