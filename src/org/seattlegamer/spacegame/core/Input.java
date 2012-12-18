package org.seattlegamer.spacegame.core;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public final class Input implements KeyInput, PointerInput, KeyListener, MouseListener, MouseMotionListener {

	private Point mouseLocation;
	private final Map<Integer, Boolean> keyInputsPressed;
	private final Map<Integer, Boolean> mouseInputsPressed;

	public Input() {
		this.mouseLocation = new Point();
		this.keyInputsPressed = new HashMap<Integer, Boolean>();
		this.mouseInputsPressed = new HashMap<Integer, Boolean>();
	}

	public boolean isKeyInputActive(Integer code) {
		Boolean pressed = this.keyInputsPressed.get(code);
		return !(pressed == null || pressed == false);
	}

	public boolean isPointerInputActive(Integer code) {
		Boolean pressed = this.mouseInputsPressed.get(code);
		return !(pressed == null || pressed == false);
	}

	public Point getMouseLocation() {
		return this.mouseLocation;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyInputsPressed.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyInputsPressed.put(e.getKeyCode(), false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseInputsPressed.put(e.getButton(), true);
		mouseLocation = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseInputsPressed.put(e.getButton(), false);
		mouseLocation = e.getPoint();
	}

	@Override public void keyTyped(KeyEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

}
