package org.seattlegamer.spacegame;

import java.io.IOException;

import org.seattlegamer.spacegame.resources.ResourceCache;

public interface State {
	void load(ComponentBus bus, ResourceCache resourceCache) throws IOException;
	void update(Input input, long elapsedMillis);
	void render(Renderer renderer);
}
