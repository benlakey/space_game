package org.seattlegamer.spacegame;

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
			
			for(Entity entity : this.stateManager.getEntities()) {
				entity.update(this.input, elapsedMillis);
			}
			this.renderer.render(this.stateManager.getEntities());

		}

	}

}
