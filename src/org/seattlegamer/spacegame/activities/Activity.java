package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.commands.Command;

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
