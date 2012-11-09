package org.seattlegamer.spacegame.communication;

import java.util.Collection;
import java.util.LinkedList;


public class CommunicationBus implements Bus {

	private Collection<Handler> handlers = new LinkedList<Handler>();
	
	@Override
	public void register(Handler handler) {
		if(this.handlers.contains(handler)) {
			return;
		}
		this.handlers.add(handler);
	}

	@Override
	public void deregister(Handler handler) {
		this.handlers.remove(handler);
	}

	@Override
	public void send(Command command) {
		for(Handler handler : this.handlers) {
			if(handler.canHandle(command)) {
				handler.handle(command);
			}
		}
	}

}
