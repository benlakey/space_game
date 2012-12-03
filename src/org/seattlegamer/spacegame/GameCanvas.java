package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public final class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_COUNT = 2;
	
	private final Frame frame;

	public GameCanvas(String title) {

		this.frame = new Frame(title);
		this.frame.add(this);
		
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		this.setScreenSize();

		this.setIgnoreRepaint(true);
		this.frame.setIgnoreRepaint(true);

		this.frame.pack();
		this.frame.setVisible(true);

		this.createBufferStrategy(BUFFER_COUNT);
		this.requestFocus();

	}

	private void setScreenSize() {
		
		//TODO: set display modes instead of just running with the current default
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height);

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		
		if (!graphicsDevice.isFullScreenSupported()) {
            throw new RuntimeException("Full screen mode not supported.");
        }
		
		graphicsDevice.setFullScreenWindow(this.frame);

	}

}
