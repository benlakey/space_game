package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class WindowedGameCanvas extends Canvas implements GameCanvas {

	private static final long serialVersionUID = 1L;
	private static final int bufferCount = 2;

	public WindowedGameCanvas(String title, KeyboardInput keyboardInput, int width, int height) {

		this.setBounds(0, 0, width, height);
		this.setIgnoreRepaint(true);

		Frame frame = new Frame(title);
		frame.add(this);
		frame.setIgnoreRepaint(true);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addKeyListener(keyboardInput);
		
		this.addKeyListener(keyboardInput);
		this.createBufferStrategy(bufferCount);
		this.requestFocus();
		
		GraphicsUtils.setWindowReference(frame);

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