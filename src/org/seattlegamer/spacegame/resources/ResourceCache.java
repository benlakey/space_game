package org.seattlegamer.spacegame.resources;

import java.awt.Image;
import java.io.IOException;

public interface ResourceCache {
	Image getImage(String name) throws IOException;
}