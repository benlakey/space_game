package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.Iterator;

public class CanvasRenderer implements Renderer {
	
	private final Canvas canvas;

	public CanvasRenderer(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void render(Iterable<Component> components) {

		BufferStrategy bufferStrategy = this.canvas.getBufferStrategy();
		Graphics2D graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
		
		try {

			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.clearScreen(graphics);
			
			//warning! dont change this to a for(:) style loop, because we are modifying this collection inside the game as we iterate!
			Iterator<Component> componentIterator = components.iterator();
			while (componentIterator.hasNext()) {
				Component component = componentIterator.next();
				component.render(graphics);
			}

			bufferStrategy.show();

		} finally {
			graphics.dispose();
		}

	}
	
	private void clearScreen(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}
	
}
