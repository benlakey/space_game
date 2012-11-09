package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.activities.Activity;
import org.seattlegamer.spacegame.activities.MainMenuActivity;
import org.seattlegamer.spacegame.communication.ActivityTransitionHandler;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ExitGameHandler;

public class Engine {

	private static Logger logger = Logger.getLogger(Engine.class);
	
	private boolean running;
	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final Bus bus;
	private final KeyboardInput keyboardInput;
	private final MouseInput mouseInput;
	private final RateLimiter rateLimiter;
	private Activity currentActivity;

	public Engine(Renderer renderer, Bus bus, KeyboardInput keyboardInput, MouseInput mouseInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.bus = bus;
		this.keyboardInput = keyboardInput;
		this.mouseInput = mouseInput;
		this.rateLimiter = rateLimiter;
		
		this.bus.register(new ActivityTransitionHandler(this));
		this.bus.register(new ExitGameHandler());
		
		this.setActivity(new MainMenuActivity(this.bus));
	}
	
	public void setActivity(Activity startActivity) {
		this.currentActivity = startActivity;
		this.attachInputControlToCurrentActivity();
	}
	
	private void attachInputControlToCurrentActivity() {

		logger.info("Attaching input control to activity: " + this.currentActivity);
		
		this.keyboardInput.setKeyListener(this.currentActivity);
		this.mouseInput.setMouseListener(this.currentActivity);
		this.mouseInput.setMouseMotionListener(this.currentActivity);

	}
	
	public void run() {

		this.running = true;

		while(running) {
			
			long now = System.currentTimeMillis();
			long elapsed = now - this.lastLoopTimestamp;
			this.lastLoopTimestamp = now;
			
			long elapsedTimeMillis = elapsed;

			this.currentActivity.update(elapsedTimeMillis);
			this.drawActivity();
	
			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());

		}
		
	}
	
	private void drawActivity() {
		Iterable<? extends Renderable> renderables = this.currentActivity.getRenderables();
		this.renderer.draw(renderables);
	}

}
