package org.seattlegamer.spacegame;

import java.util.Iterator;

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
			long remaining = throttle.timeRemaining();
			if(remaining != 0) {
				Thread.sleep(remaining);
			} else {
				throttle.throttle();
			}
			
			Iterable<Component> components = this.stateManager.getComponents();
			
			//warning! dont change this to a for(:) style loop, because we are modifying this collection inside the game as we iterate!
			Iterator<Component> componentIterator = components.iterator();
			while (componentIterator.hasNext()) {
				Component component = componentIterator.next();
				component.update(this.input, elapsedMillis);
			}

			this.renderer.render(components);

		}

	}

}
