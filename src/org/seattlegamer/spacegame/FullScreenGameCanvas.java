package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class FullScreenGameCanvas extends Canvas implements GameCanvas {

	private static final long serialVersionUID = 1L;
	private static final int bufferCount = 2;

	public FullScreenGameCanvas(String title, KeyboardInput keyboardInput, MouseInput mouseInput) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setBounds(0, 0, screenSize.width, screenSize.height);
		this.setIgnoreRepaint(true);

		Frame frame = new Frame(title);
		frame.add(this);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		this.addKeyListener(keyboardInput);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
		
		this.setFullscreen(frame);
		this.createBufferStrategy(bufferCount);
		this.requestFocus();

	}

	private void setFullscreen(Frame frame) {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		
		if (!graphicsDevice.isFullScreenSupported()) {
            throw new RuntimeException("Full screen mode not supported.");
        }

		//TODO: Oracle introduced a bug:  http://stackoverflow.com/questions/13064607
		//Uncomment the following once there is resolution to that issue.
		//graphicsDevice.setFullScreenWindow(frame);
		
		//Alternative true fullscreen on Mac OS X for debugging purposes (do not use in production code!)
		//enableOSXFullscreen(frame);
		
		//TODO: set display modes instead of just running with the current default
	}
	
	
	
	@Override
	public Graphics2D getGraphics() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		return (Graphics2D)bufferStrategy.getDrawGraphics();
	}

	@Override
	public void showNextBuffer() {
		this.getBufferStrategy().show();
	}

}
