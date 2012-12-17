package org.seattlegamer.spacegame.core;

import java.io.IOException;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.resources.ResourceCache;

public interface State {
	void load(ResourceCache resourceCache, StateManager stateManager, GameSettings settings) throws IOException;
	void addComponent(Component component);
	void removeComponent(Component component);
	void update(Input input, long elapsedMillis);
	void render(Renderer renderer);
}
