package org.seattlegamer.spacegame;

public interface Activity {
	Renderable[] getRenderables();
	void sendCommand(Command command);
}
