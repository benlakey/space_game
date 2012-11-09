package org.seattlegamer.spacegame.communication;


public interface Bus {
	void register(Handler handler);
	void deregister(Handler handler);
	void send(Command command);
}
