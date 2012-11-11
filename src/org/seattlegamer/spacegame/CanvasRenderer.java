package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.LinkedList;

public class CanvasRenderer implements Renderer {
	
	private final GameCanvas canvas;

	public CanvasRenderer(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void draw(Renderable renderable) {
		Collection<Renderable> renderables = new LinkedList<Renderable>();
		renderables.add(renderable);
		this.draw(renderables);
	}
	
	@Override
	public void draw(Iterable<? extends Renderable> itemsToRender) {
		
		Graphics2D graphics = this.canvas.getGraphics();

		try {

			this.clearScreen(graphics);
	
			if(itemsToRender != null) {
				for(Renderable renderable : itemsToRender) {
					renderable.render(graphics);
				}
			}

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
