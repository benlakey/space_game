package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.activities.Activity;
import org.seattlegamer.spacegame.activities.MainMenuActivity;
import org.seattlegamer.spacegame.communication.ActivityTransitionHandler;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ExitGameHandler;
import org.seattlegamer.spacegame.communication.NewGameHandler;

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
	private Activity nextActivity;
	private static final Object activityLock = new Object();

	public Engine(Renderer renderer, Bus bus, KeyboardInput keyboardInput, MouseInput mouseInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.bus = bus;
		this.keyboardInput = keyboardInput;
		this.mouseInput = mouseInput;
		this.rateLimiter = rateLimiter;
		
		this.bus.register(new ActivityTransitionHandler(this));
		this.bus.register(new ExitGameHandler());
		this.bus.register(new NewGameHandler(this.bus));
		
		this.setActivity(new MainMenuActivity(this.bus));
	}
	
	public void setActivity(Activity activity) {
		synchronized(activityLock) {
			this.nextActivity = activity;
		}
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

			synchronized(activityLock) {
				if(this.nextActivity != null) {
					this.currentActivity = this.nextActivity;
					this.attachInputControlToCurrentActivity();
					this.nextActivity = null;
				}
			}
			
			this.currentActivity.update(elapsedTimeMillis);
			this.renderer.draw(this.currentActivity);

			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());

		}
		
	}

}
