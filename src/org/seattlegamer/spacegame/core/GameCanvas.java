package org.seattlegamer.spacegame.core;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Frame;

public final class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_COUNT = 2;
	
	private final Frame frame;

	public GameCanvas(Input input, String title, DisplayMode displayMode) {

		this.frame = new Frame(title);
		this.frame.add(this);

		this.frame.setUndecorated(true);
		this.frame.setResizable(false);

		this.setBounds(0, 0, displayMode.getWidth(), displayMode.getHeight());
		
		this.setIgnoreRepaint(true);
		this.frame.setIgnoreRepaint(true);
		
		this.frame.pack();
		this.frame.setVisible(true);

		this.createBufferStrategy(BUFFER_COUNT);
		this.requestFocus();
		
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);

	}

}
