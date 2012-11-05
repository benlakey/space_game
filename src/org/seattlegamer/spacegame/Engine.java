package org.seattlegamer.spacegame;


public class Engine {

	//private static Logger logger = Logger.getLogger(Engine.class);
	
	private boolean running;
	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final KeyboardInput keyboardInput;
	private final RateLimiter rateLimiter;
	private Activity currentActivity;
	
	public Engine(Renderer renderer, KeyboardInput keyboardInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.keyboardInput = keyboardInput;
		this.rateLimiter = rateLimiter;
	}
	
	public void setActivity(Activity startActivity) {
		this.currentActivity = startActivity;
		this.keyboardInput.setKeyListener(this.currentActivity);
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

			this.think(elapsedTimeMillis);
			this.drawActivity();
	
			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());

		}
		
	}
	
	private void drawActivity() {
		Iterable<? extends Renderable> renderables = this.currentActivity.getRenderables();
		this.renderer.draw(renderables);
	}
	
	private void think(long elapsedTimeMillis) {
		//TODO: act based on inputs, and position things based on elapsedTimeMillis
	}

}
