package org.seattlegamer.spacegame.communication;


public interface Handler {
	<T extends Command> boolean canHandle(T command);
	void handle(Command command);
}
