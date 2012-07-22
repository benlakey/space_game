package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apache.log4j.Logger;

public class Engine {
	
	private static Logger logger = Logger.getLogger(Engine.class);
	private static final int targetMaxFramerate = 100;
	private static final int framerateDelayMillis = 1000 / targetMaxFramerate;
	
	private GameCanvas canvas;
	private boolean running;
	private long lastLoopTimestamp;
	
	public Engine(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void run() {
		
		this.running = true;

		while(running) {
			
			long now = System.currentTimeMillis();
			long elapsed = now - this.lastLoopTimestamp;
			this.lastLoopTimestamp = now;
			
			//TODO: implement. (outside of this class, SRP).
			//this.processInput();
			//this.processWorld(elapsed);
			
			this.drawWorld();

			this.enforceMaxFramerate();
		}
		
	}
	
	private void drawWorld() {
		
		Graphics2D graphics = this.canvas.getGraphics();
		
		this.resetScreen(graphics);
		
		//TODO: draw, using a class outside of Engine (SRP).
		
		graphics.dispose();
		
		this.canvas.showNextBuffer();
	}

	private void resetScreen(Graphics2D graphics) {
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		
	}
	
	private void enforceMaxFramerate() {
		
		try {
			Thread.sleep(framerateDelayMillis);
		} catch(InterruptedException ex) {
			logger.warn("Something interrupted the framerate limiter.");
		}
		
	}

}
