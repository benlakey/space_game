package org.seattlegamer.spacegame.core;

import org.apache.log4j.Logger;

public class Engine {

	private static final Logger logger = Logger.getLogger(Engine.class);
	
	private long lastMillis;
	private final int millisPerFrame;
	private final KeyInput keyInput;
	private final PointerInput pointerInput;
	private final Renderer renderer;
	private final StateManager stateManager;

	public Engine(int targetFramerate, KeyInput keyInput, PointerInput pointerInput, Renderer renderer, StateManager stateManager) {
		this.millisPerFrame = 1000 / targetFramerate;
		this.keyInput = keyInput;
		this.pointerInput = pointerInput;
		this.renderer = renderer;
		this.stateManager = stateManager;
	}

	public void run() throws InterruptedException {

		while(true) {

			long nowMillis = System.currentTimeMillis();
			long elapsedMillis = nowMillis - this.lastMillis;
			this.lastMillis = nowMillis;

			this.stateManager.update(this.keyInput, this.pointerInput, elapsedMillis);
			this.stateManager.render(this.renderer);
			
			long cutOff = this.lastMillis + this.millisPerFrame;
			long toSleepFor = cutOff - System.currentTimeMillis();
			toSleepFor = Math.max(toSleepFor, 0);
			
			try {
				Thread.sleep(toSleepFor);
			} catch(InterruptedException e) {
				logger.error("Unable to maintain constant update rate in engine.", e);
			}

		}

	}

}
