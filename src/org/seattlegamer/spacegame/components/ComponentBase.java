package org.seattlegamer.spacegame.components;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.Renderable;

//TODO: decouple ourselves from AWT and make these implement our own pointing device and key entry abstractions
public abstract class ComponentBase implements Renderable, KeyListener, MouseListener, MouseMotionListener {

	protected final Collection<ComponentBase> subComponents = new LinkedList<ComponentBase>();
	
	private boolean enableInput;
	private boolean isVisible;
	
	protected ComponentBase() {
		this.enableInput = true;
		this.isVisible = true;
	}
	
	protected ComponentBase(boolean enableInput, boolean isVisible) {
		this.enableInput = enableInput;
		this.isVisible = isVisible;
	}
	
	public boolean inputIsEnabled() {
		return this.enableInput;
	}

	public void enableInput(boolean enableInput) {
		this.enableInput = enableInput;
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void update(long elapsedTimeMillis) {
		for(ComponentBase gameComponent : this.subComponents) {
			gameComponent.update(elapsedTimeMillis);
		}
	}
	
	@Override
	public void render(Graphics2D graphics) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.isVisible()) {
				gameComponent.render(graphics);
			}
		}
	}

	@Override 
	public void mouseDragged(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseDragged(e);
			}
		}
	}

	@Override 
	public void mouseMoved(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseMoved(e);
			}
		}
	}

	@Override 
	public void mouseClicked(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseClicked(e);
			}
		}
	}
	
	@Override 
	public void mousePressed(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mousePressed(e);
			}
		}
	}
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseReleased(e);
			}
		}
	}
	
	@Override 
	public void mouseEntered(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseEntered(e);
			}
		}
	}

	@Override 
	public void mouseExited(MouseEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.mouseExited(e);
			}
		}
	}

	@Override 
	public void keyTyped(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.keyTyped(e);
			}
		}
	}

	@Override 
	public void keyPressed(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.keyPressed(e);
			}
		}
	}

	@Override 
	public void keyReleased(KeyEvent e) {
		for(ComponentBase gameComponent : this.subComponents) {
			if(gameComponent.inputIsEnabled()) {
				gameComponent.keyReleased(e);
			}
		}
	}
	
}
