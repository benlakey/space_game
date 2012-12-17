package org.seattlegamer.spacegame.core;

import org.seattlegamer.spacegame.utils.Throttle;

public class Engine {

	private long lastMillis;
	private final Throttle throttle;
	private final Input input;
	private final Renderer renderer;
	private final StateManager stateManager;

	public Engine(Throttle throttle, Input input, Renderer renderer, StateManager stateManager) {
		this.throttle = throttle;
		this.input = input;
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

			this.stateManager.update(this.input, elapsedMillis);
			this.stateManager.render(this.renderer);

		}

	}

}
