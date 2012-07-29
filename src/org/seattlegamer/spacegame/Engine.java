package org.seattlegamer.spacegame;

import java.awt.Graphics2D;

import org.apache.log4j.Logger;

public class Engine {

	private static final Logger logger = Logger.getLogger(Engine.class);
	private static final int targetMaxFramerate = 100;
	private static final long framerateDelayMillis = 1000 / targetMaxFramerate;
	
	private GameCanvas canvas;
	private boolean running;
	private long lastLoopTimestamp;
	
	public Engine(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void run() {
		
		this.running = true;
		RateLimiter rateLimiter = new RateLimiter(framerateDelayMillis);

		while(running) {
			
			long elapsedTimeMillis = this.determineElapsedMillisSinceLastLoop();
			
			//TODO: implement. (outside of this class, SRP).
			//this.processInput();
			//this.processWorld(elapsed);
			
			this.drawWorld();

			rateLimiter.blockAsNeeded(System.currentTimeMillis());
		}
		
	}
	
	private long determineElapsedMillisSinceLastLoop() {
		
		long now = System.currentTimeMillis();
		long elapsed = now - this.lastLoopTimestamp;
		this.lastLoopTimestamp = now;
		
		return elapsed;

	}
	
	private void drawWorld() {
		
		this.canvas.clear();

		Graphics2D graphics = this.canvas.getGraphics();
		//TODO: draw, using a class outside of Engine (SRP).
		graphics.dispose();
		
		this.canvas.showNextBuffer();
	}

}
