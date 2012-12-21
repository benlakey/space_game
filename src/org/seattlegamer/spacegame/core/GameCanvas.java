package org.seattlegamer.spacegame.core;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Frame;

public final class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_COUNT = 2;
	
	private final Frame frame;

	public GameCanvas(Input input, String title, DisplayMode displayMode) {
		this.frame = new Frame();
		this.configureCanvas(displayMode);
		this.configureFrame(title);
		this.createBufferStrategy(BUFFER_COUNT);
		this.configureInput(input);
	}
	
	private void configureCanvas(DisplayMode displayMode) {
		this.setBounds(0, 0, displayMode.getWidth(), displayMode.getHeight());
		this.setIgnoreRepaint(true);
	}
	
	private void configureFrame(String title) {
		this.frame.setTitle(title);
		this.frame.add(this);
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		this.frame.setIgnoreRepaint(true);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void configureInput(Input input) {
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.requestFocus();
	}

}
