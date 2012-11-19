package org.seattlegamer.spacegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import org.seattlegamer.spacegame.utils.Throttle;

public class Engine {

	private long lastMillis;
	private final Throttle throttle;
	private final Input input;
	private final Canvas canvas;
	private final StateManager stateManager;

	public Engine(Throttle throttle, Input input, Canvas canvas, StateManager stateManager) {
		this.throttle = throttle;
		this.input = input;
		this.canvas = canvas;
		this.stateManager = stateManager;
	}

	public void run() throws InterruptedException {

		this.stateManager.handle(new OpenMenu());
		
		while(true) {

			long nowMillis = System.currentTimeMillis();
			long elapsedMillis = nowMillis - this.lastMillis;
			this.lastMillis = nowMillis;
			
			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();
			if(remaining != 0) {
				Thread.sleep(remaining);
			} else {
				throttle.rethrottle();
			}
			
			this.updateEntities(elapsedMillis);
			this.renderEntities();

		}

	}
	
	private void clearScreen(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}
	
	private void updateEntities(long elapsedMillis) {
		for(Entity entity : this.stateManager.getEntities()) {
			entity.update(this.input, elapsedMillis);
		}
	}
	
	private void renderEntities() {

		BufferStrategy bufferStrategy = this.canvas.getBufferStrategy();
		Graphics2D graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
		
		try {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.clearScreen(graphics);
			for(Entity entity : this.stateManager.getEntities()) {
				entity.render(graphics);
			}
			bufferStrategy.show();
		} finally {
			graphics.dispose();
		}

	}

}
