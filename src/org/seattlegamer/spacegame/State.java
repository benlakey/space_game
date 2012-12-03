package org.seattlegamer.spacegame;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.resources.ResourceCache;

public abstract class State {

	protected final Collection<Component> components;

	public State() {
		this.components = new LinkedList<Component>();
	}

	public abstract void load(Bus bus, ResourceCache resourceCache) throws IOException;

	public Iterable<Component> getComponents() {
		return this.components;
	}

}
