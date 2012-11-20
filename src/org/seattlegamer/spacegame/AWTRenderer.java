package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

public class AWTRenderer implements Renderer {
	
	private final Canvas canvas;

	public AWTRenderer(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void render(Iterable<Entity> entities) {

		BufferStrategy bufferStrategy = this.canvas.getBufferStrategy();
		Graphics2D graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
		
		try {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.clearScreen(graphics);
			for(Entity entity : entities) {
				entity.render(graphics);
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
