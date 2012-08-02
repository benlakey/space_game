package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;

public class Engine {

	private static Logger logger = Logger.getLogger(Engine.class);
	
	private boolean running;
	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final RateLimiter rateLimiter;
	
	public Engine(Renderer renderer, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.rateLimiter = rateLimiter;
	}
	
	public void run() {
		
		this.running = true;
	
		while(running) {
			
			long elapsedTimeMillis = this.calculateLoopElapsedTime();
			logger.info("elapsedTimeMillis:" + elapsedTimeMillis);
	
			this.processInput();
			this.think(elapsedTimeMillis);
			this.drawWorld();
	
			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());
		}
		
	}
	
	private long calculateLoopElapsedTime() {
		
		long now = System.currentTimeMillis();
		long elapsed = now - this.lastLoopTimestamp;
		this.lastLoopTimestamp = now;
		
		return elapsed;
	
	}
	
	private void processInput() {
		//TODO: accept user input and apply it to the player entity.
	}
	
	private void think(long elapsedTimeMillis) {
		//TODO: act based on inputs, and position things based on elapsedTimeMillis
	}
	
	private void drawWorld() {
		//TODO: this is temporary just for compilation sake. Ultimately we'll have some way of managing all sprites.
		Sprite[] sprites = new Sprite[0];
		this.renderer.draw(sprites);
	}

}
