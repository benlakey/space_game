package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Graphics2D;

public class CanvasRenderer implements Renderer {

	private final GameCanvas canvas;

	public CanvasRenderer(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void draw(Iterable<Renderable> itemsToRender) {
		
		Graphics2D graphics = this.canvas.getGraphics();
		
		this.clearScreen(graphics);

		for(Renderable renderable : itemsToRender) {
			renderable.render(graphics);
		}
		
		graphics.dispose();
		
		this.canvas.showNextBuffer();
	}

	private void clearScreen(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}
}
