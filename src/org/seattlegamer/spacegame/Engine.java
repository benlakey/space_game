package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.activities.ComponentBase;
import org.seattlegamer.spacegame.activities.MainMenuComponent;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransitionHandler;
import org.seattlegamer.spacegame.communication.ExitGameHandler;
import org.seattlegamer.spacegame.communication.NewGameHandler;

public class Engine {

	private static Logger logger = Logger.getLogger(Engine.class);
	private static final Object componentLock = new Object();
	
	private boolean running;
	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final Bus bus;
	private final KeyboardInput keyboardInput;
	private final MouseInput mouseInput;
	private final RateLimiter rateLimiter;
	private ComponentBase currentComponent;
	private ComponentBase nextComponent;

	public Engine(Renderer renderer, Bus bus, KeyboardInput keyboardInput, MouseInput mouseInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.bus = bus;
		this.keyboardInput = keyboardInput;
		this.mouseInput = mouseInput;
		this.rateLimiter = rateLimiter;
		
		this.bus.register(new ComponentTransitionHandler(this));
		this.bus.register(new ExitGameHandler());
		this.bus.register(new NewGameHandler(this.bus));
		
		this.setComponent(new MainMenuComponent(this.bus));
	}
	
	public void setComponent(ComponentBase component) {
		synchronized(componentLock) {
			this.nextComponent = component;
		}
	}
	
	private void attachInputControlToCurrentComponent() {

		logger.info("Attaching input control to component: " + this.currentComponent);
		
		this.keyboardInput.setKeyListener(this.currentComponent);
		this.mouseInput.setMouseListener(this.currentComponent);
		this.mouseInput.setMouseMotionListener(this.currentComponent);

	}
	
	public void run() {

		this.running = true;

		while(running) {
			
			long now = System.currentTimeMillis();
			long elapsed = now - this.lastLoopTimestamp;
			this.lastLoopTimestamp = now;
			
			long elapsedTimeMillis = elapsed;

			synchronized(componentLock) {
				if(this.nextComponent != null) {
					this.currentComponent = this.nextComponent;
					this.attachInputControlToCurrentComponent();
					this.nextComponent = null;
				}
			}
			
			this.currentComponent.update(elapsedTimeMillis);
			this.renderer.draw(this.currentComponent);

			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());

		}
		
	}

}
