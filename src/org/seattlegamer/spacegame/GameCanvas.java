package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public final class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_COUNT = 2;
	
	private final Frame frame;

	public GameCanvas(Input input, String title, DisplayMode displayMode) {

		this.frame = new Frame(title);
		this.frame.add(this);

		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		this.setScreenSize(displayMode);
		
		this.setIgnoreRepaint(true);
		this.frame.setIgnoreRepaint(true);
		
		this.frame.pack();
		this.frame.setVisible(true);

		this.createBufferStrategy(BUFFER_COUNT);
		this.requestFocus();
		
		this.addKeyListener(input.getKeyListener());
		this.addMouseListener(input.getMouseListener());
		this.addMouseMotionListener(input.getMouseMotionListener());

	}

	private void setScreenSize(DisplayMode displayMode) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height);

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		
		if (!graphicsDevice.isFullScreenSupported()) {
            throw new RuntimeException("Full screen mode not supported.");
        }
		
		graphicsDevice.setFullScreenWindow(this.frame);

		if(graphicsDevice.isDisplayChangeSupported()) {
			graphicsDevice.setDisplayMode(displayMode);
		}

	}

}
