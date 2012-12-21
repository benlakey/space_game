package org.seattlegamer.spacegame.core;

public class Engine {

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
			
			Thread.sleep(toSleepFor);

		}

	}

}
