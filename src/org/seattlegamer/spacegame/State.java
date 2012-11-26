package org.seattlegamer.spacegame;

public interface State {
	void load(Handler stateChangeHandler);
	Iterable<Entity> getEntities();
}
