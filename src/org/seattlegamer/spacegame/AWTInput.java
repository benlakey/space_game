package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AWTInput implements Input, KeyListener, MouseListener, MouseMotionListener {

	private static final Map<Integer, Integer> AWT_KEYCODE_MAP;
	private static final Map<Integer, Integer> AWT_POINTERBUTTON_MAP;
	
	private Point pointer;
	private final Map<Integer, Boolean> keyInputsPressed;
	private final Map<Integer, Boolean> pointerInputsPressed;
	
	static {
		Map<Integer, Integer> awtKeyCodeMap = new HashMap<Integer, Integer>();

		awtKeyCodeMap.put(KeyEvent.VK_UP, InputCode.Keyboard.UP);
		awtKeyCodeMap.put(KeyEvent.VK_DOWN, InputCode.Keyboard.DOWN);
		awtKeyCodeMap.put(KeyEvent.VK_LEFT, InputCode.Keyboard.LEFT);
		awtKeyCodeMap.put(KeyEvent.VK_RIGHT, InputCode.Keyboard.RIGHT);
		awtKeyCodeMap.put(KeyEvent.VK_ENTER, InputCode.Keyboard.ACTION_PRIMARY);
		
		for(int i = 'a'; i <= 'z'; i++) {
			awtKeyCodeMap.put(i, i);
		}
		
		AWT_KEYCODE_MAP = Collections.unmodifiableMap(awtKeyCodeMap);
		
		Map<Integer, Integer> mouseButtonMap = new HashMap<Integer, Integer>();
		
		mouseButtonMap.put(MouseEvent.BUTTON1, InputCode.Pointer.MOUSE_1);
		
		AWT_POINTERBUTTON_MAP = Collections.unmodifiableMap(mouseButtonMap);
	}
	
	public AWTInput() {
		this.pointer = new Point();
		this.keyInputsPressed = new HashMap<Integer, Boolean>();
		this.pointerInputsPressed = new HashMap<Integer, Boolean>();
	}
	
	@Override
	public boolean isKeyInputActive(Integer code) {
		Boolean pressed = this.keyInputsPressed.get(code);
		return !(pressed == null || pressed == false);
	}
	
	@Override
	public boolean isPointerInputActive(Integer code) {
		Boolean pressed = this.pointerInputsPressed.get(code);
		return !(pressed == null || pressed == false);
	}
	
	@Override
	public int getPointerX() {
		return this.pointer.x;
	}

	@Override
	public int getPointerY() {
		return this.pointer.y;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.pointer = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.pointer = e.getPoint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int awtCode = e.getButton();
		Integer code = AWT_POINTERBUTTON_MAP.get(awtCode);
		this.pointerInputsPressed.put(code, true);
		this.pointer = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int awtCode = e.getButton();
		Integer code = AWT_POINTERBUTTON_MAP.get(awtCode);
		this.pointerInputsPressed.put(code, false);
		this.pointer = e.getPoint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int awtCode = e.getKeyCode();
		Integer code = AWT_KEYCODE_MAP.get(awtCode);
		this.keyInputsPressed.put(code, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int awtCode = e.getKeyCode();
		Integer code = AWT_KEYCODE_MAP.get(awtCode);
		this.keyInputsPressed.put(code, false);
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}

}
