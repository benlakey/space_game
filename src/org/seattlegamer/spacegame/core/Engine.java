package org.seattlegamer.spacegame.core;

import org.seattlegamer.spacegame.utils.Throttle;

public class Engine {

	private long lastMillis;
	private final Throttle throttle;
	private final KeyInput keyInput;
	private final PointerInput pointerInput;
	private final Renderer renderer;
	private final StateManager stateManager;

	public Engine(Throttle throttle, KeyInput keyInput, PointerInput pointerInput, Renderer renderer, StateManager stateManager) {
		this.throttle = throttle;
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
			
			throttle.tick(elapsedMillis);
			long millisUntilExecution = throttle.getMillisUntilExecution();
			if(millisUntilExecution != 0) {
				Thread.sleep(millisUntilExecution);
				throttle.unthrottle();
			}

			this.stateManager.update(this.keyInput, this.pointerInput, elapsedMillis);
			this.stateManager.render(this.renderer);

		}

	}

}
