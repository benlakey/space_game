package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public final class Input {

	private Point mouseLocation;
	private final Map<Integer, Boolean> keyInputsPressed;
	private final Map<Integer, Boolean> mouseInputsPressed;
	private final KeyListener keyListener;
	private final MouseListener mouseListener;
	private final MouseMotionListener mouseMotionListener;

	public Input() {
		this.mouseLocation = new Point();
		this.keyInputsPressed = new HashMap<Integer, Boolean>();
		this.mouseInputsPressed = new HashMap<Integer, Boolean>();
		this.keyListener = new KeyboardInput();
		MouseInput mouseInput = new MouseInput();
		this.mouseListener = mouseInput;
		this.mouseMotionListener = mouseInput;
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

	public KeyListener getKeyListener() {
		return this.keyListener;
	}

	public MouseListener getMouseListener() {
		return this.mouseListener;
	}

	public MouseMotionListener getMouseMotionListener() {
		return this.mouseMotionListener;
	}

	private class KeyboardInput implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			keyInputsPressed.put(e.getKeyCode(), true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyInputsPressed.put(e.getKeyCode(), false);
		}

		@Override public void keyTyped(KeyEvent e) {}

	}
	
	private class MouseInput implements MouseListener, MouseMotionListener {
		
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

		@Override public void mouseClicked(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}
		
	}

}
