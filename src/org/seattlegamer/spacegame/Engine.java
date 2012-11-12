package org.seattlegamer.spacegame;

import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.ExitGame;
import org.seattlegamer.spacegame.communication.Handler;
import org.seattlegamer.spacegame.components.ComponentBase;
import org.seattlegamer.spacegame.components.MainMenuComponent;

public class Engine implements Handler {

	private volatile boolean stopRequested;
	private volatile ComponentBase currentComponent;

	private long lastLoopTimestamp;
	private final Renderer renderer;
	private final Bus bus;
	private final KeyboardInput keyboardInput;
	private final MouseInput mouseInput;
	private final RateLimiter rateLimiter;

	public Engine(Renderer renderer, Bus bus, KeyboardInput keyboardInput, MouseInput mouseInput, RateLimiter rateLimiter) {
		this.renderer = renderer;
		this.bus = bus;
		this.keyboardInput = keyboardInput;
		this.mouseInput = mouseInput;
		this.rateLimiter = rateLimiter;

		this.setComponent(new MainMenuComponent(this.bus));
	}
	
	public void setComponent(ComponentBase component) {
		
		this.currentComponent = component;
		
		this.keyboardInput.setKeyListener(this.currentComponent);
		this.mouseInput.setMouseListener(this.currentComponent);
		this.mouseInput.setMouseMotionListener(this.currentComponent);
		
	}

	public void run() {

		this.stopRequested = false;

		while(!this.stopRequested) {
			
			long now = System.currentTimeMillis();
			long elapsed = now - this.lastLoopTimestamp;
			this.lastLoopTimestamp = now;
			
			long elapsedTimeMillis = elapsed;

			this.currentComponent.update(elapsedTimeMillis);
			this.renderer.draw(this.currentComponent);

			this.rateLimiter.blockAsNeeded(System.currentTimeMillis());

		}
		
		ExitGame exitGameCommand = new ExitGame();
		exitGameCommand.setEngineStopped(true);
		
		this.bus.send(exitGameCommand);

	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof ExitGame || command instanceof ComponentTransition;
	}

	@Override
	public void handle(Command command) {
		
		if(command instanceof ExitGame) {
			ExitGame exitGameCommand = (ExitGame)command;
			if(!exitGameCommand.isEngineStopped()) {
				this.stopRequested = true;
			}
		} else if(command instanceof ComponentTransition) {
			ComponentTransition transition = (ComponentTransition)command;
			ComponentBase newComponent = transition.getNewComponent();
			this.setComponent(newComponent);
		}
		
		
	}

}
