package org.seattlegamer.spacegame;

import java.awt.event.KeyListener;

public interface Activity extends KeyListener {
	Iterable<Renderable> getRenderables();
}
