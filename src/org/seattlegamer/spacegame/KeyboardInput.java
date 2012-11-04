package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	private KeyListener innerKeyListener;
	
	public void setKeyListener(KeyListener keyListener) {
		this.innerKeyListener = keyListener;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(this.innerKeyListener == null) {
			return;
		}
		this.innerKeyListener.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(this.innerKeyListener == null) {
			return;
		}
		this.innerKeyListener.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(this.innerKeyListener == null) {
			return;
		}
		this.innerKeyListener.keyReleased(e);
	}

}
