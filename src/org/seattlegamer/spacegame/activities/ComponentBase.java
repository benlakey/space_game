package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Renderable;

public abstract class ComponentBase implements Renderable, KeyListener, MouseListener, MouseMotionListener {

	protected final Collection<ComponentBase> subComponents = new LinkedList<ComponentBase>();
	
	public void update(long elapsedTimeMillis) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.update(elapsedTimeMillis);
		}
	}

	@Override 
	public void mouseDragged(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseDragged(e);
		}
	}

	@Override 
	public void mouseMoved(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseMoved(e);
		}
	}

	@Override 
	public void mouseClicked(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseClicked(e);
		}
	}
	
	@Override 
	public void mousePressed(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mousePressed(e);
		}
	}
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseReleased(e);
		}
	}
	
	@Override 
	public void mouseEntered(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseEntered(e);
		}
	}

	@Override 
	public void mouseExited(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.mouseExited(e);
		}
	}

	@Override 
	public void keyTyped(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.keyTyped(e);
		}
	}

	@Override 
	public void keyPressed(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.keyPressed(e);
		}
	}

	@Override 
	public void keyReleased(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.keyReleased(e);
		}
	}
	
}
