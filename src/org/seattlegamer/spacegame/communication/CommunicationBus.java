package org.seattlegamer.spacegame.communication;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class CommunicationBus implements Bus {

	private static Logger logger = Logger.getLogger(CommunicationBus.class);
	
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
				logger.info(handler.getClass() + " handling " + command.getClass());
				handler.handle(command);
			}
		}
	}

}
