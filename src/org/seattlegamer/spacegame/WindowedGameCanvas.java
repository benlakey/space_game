package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class WindowedGameCanvas extends Canvas implements GameCanvas {

	private static final long serialVersionUID = 1L;
	private static final int windowWidth = 800;
	private static final int windowHeight = 600;
	private static final int bufferCount = 2;

	public WindowedGameCanvas(String title) {
		
		this.configureCanvas();
		this.configureFrame(title);
		this.configureAcceleratedGraphics();

	}
	
	public Graphics2D getGraphics() {
		
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		return (Graphics2D)bufferStrategy.getDrawGraphics();

	}
	
	public void showNextBuffer() {
		this.getBufferStrategy().show();
	}
	
	private void configureAcceleratedGraphics() {
		
		this.createBufferStrategy(bufferCount);
		
	}

	private void configureCanvas() {

		this.setBounds(0, 0, windowWidth, windowHeight);
		this.setIgnoreRepaint(true); //let us handle the repainting of the canvas

	}
	
	private void configureFrame(String title) {
		
		JFrame frame = new JFrame(title);
		
		Container container = frame.getContentPane();

		container.setPreferredSize(new Dimension(windowWidth, windowHeight));
		container.setLayout(null); //no layout manager (absolute positioning)
		container.add(this);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}

}
