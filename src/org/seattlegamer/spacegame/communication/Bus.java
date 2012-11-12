package org.seattlegamer.spacegame.communication;


public interface Bus extends Handler {
	void register(Handler handler);
	void deregister(Handler handler);
	void send(Command command);
}
