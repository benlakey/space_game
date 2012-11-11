package org.seattlegamer.spacegame;

public interface Renderer {
	void draw(Renderable renderable);
	void draw(Iterable<? extends Renderable> itemsToRender);
}
