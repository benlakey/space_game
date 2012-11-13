package org.seattlegamer.spacegame.resources;

import java.io.IOException;

public interface ResourceLoader<T> {
	Class<T> getLoadableType();
	T load(String name) throws IOException;
}
