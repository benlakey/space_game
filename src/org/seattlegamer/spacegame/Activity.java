package org.seattlegamer.spacegame;

public interface Activity {
	Iterable<Renderable> getRenderables();
	<T extends Command> boolean canHandle(T command);
	<T extends Command> void handle(T command);
}
