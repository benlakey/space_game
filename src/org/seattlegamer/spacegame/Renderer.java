package org.seattlegamer.spacegame;

public interface Renderer {
	void draw(Iterable<? extends Renderable> itemsToRender);
}
