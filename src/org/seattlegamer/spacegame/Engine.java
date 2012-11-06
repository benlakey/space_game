package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.activities.Activity;
import org.seattlegamer.spacegame.commands.ActivityTransitionCommand;
import org.seattlegamer.spacegame.commands.Command;
import org.seattlegamer.spacegame.commands.ExitCommandHandler;

public class Engine {

	private static Logger logger = Logger.getLogger(Engine.class);
	
	private boolean running;
	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final KeyboardInput keyboardInput;
	private final MouseInput mouseInput;
	private final RateLimiter rateLimiter;
	private Activity currentActivity;
	
	public Engine(Renderer renderer, KeyboardInput keyboardInput, MouseInput mouseInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.keyboardInput = keyboardInput;
		this.mouseInput = mouseInput;
		this.rateLimiter = rateLimiter;
	}
	
	public void setActivity(Activity startActivity) {
		
		this.currentActivity = startActivity;

		this.attachInputControlToCurrentActivity();
		this.attachCommandHandlersToCurrentActivity();

	}
	
	private void attachInputControlToCurrentActivity() {

		logger.info("Attaching input control to activity: " + this.currentActivity);
		
		this.keyboardInput.setKeyListener(this.currentActivity);
		this.mouseInput.setMouseListener(this.currentActivity);
		this.mouseInput.setMouseMotionListener(this.currentActivity);

	}
	
	private void attachCommandHandlersToCurrentActivity() {

		this.currentActivity.clearHandlers();
		this.currentActivity.attachHandler(new ExitCommandHandler());
		this.currentActivity.attachHandler(new Handler() {
			@Override
			public void handle(Command command) {
				if(command instanceof ActivityTransitionCommand) {
					ActivityTransitionCommand activityTransitionCommand = (ActivityTransitionCommand)command;
					Activity newActivity = activityTransitionCommand.getNewActivity();
					setActivity(newActivity);
				}
			}
		});

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
