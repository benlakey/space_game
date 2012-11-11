package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Graphics2D;

public class CanvasRenderer implements Renderer {
	
	private final GameCanvas canvas;

	public CanvasRenderer(GameCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void draw(Renderable renderable) {
		
		Graphics2D graphics = this.canvas.getGraphics();

		try {
			this.clearScreen(graphics);
			renderable.render(graphics);
		} finally {
			graphics.dispose();
		}
		
		this.canvas.showNextBuffer();

	}

	private void clearScreen(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}


}
