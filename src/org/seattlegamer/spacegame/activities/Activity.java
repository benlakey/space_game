package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.commands.Command;

public abstract class Activity implements KeyListener, MouseListener, MouseMotionListener {

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
	
	@Override public void mouseDragged(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
	
}
