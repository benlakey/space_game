package org.seattlegamer.spacegame;

import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;

public abstract class Activity implements KeyListener {
	
	private Collection<Handler> attachedHandlers = new LinkedList<Handler>();
	
	public abstract Iterable<? extends Renderable> getRenderables();
	public abstract void update(long elapsedTimeMillis);
	
	public void clearHandlers() {
		this.attachedHandlers.clear();
	}
	
	public void attachHandler(Handler handler) {
		this.attachedHandlers.add(handler);
	}
	
	protected void notifyListeners(Command command) {
		for(Handler handler : this.attachedHandlers) {
			handler.handle(command);
		}
	}
	
}
