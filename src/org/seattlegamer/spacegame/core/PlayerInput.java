package org.seattlegamer.spacegame.core;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.ProjectileLaunch;
import org.seattlegamer.spacegame.messages.RotationChange;
import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.utils.Throttle;

public class PlayerInput extends Component {

	private static final int ROTATION_DELAY_MILLIS = 10;
	private static final double ROTATION_INCREMENT_DEGREES = 5;

	private final Throttle rotationThrottle;
	private final StateManager stateManager;
	
	public PlayerInput(ComponentBus bus, UUID entityId, StateManager stateManager) {
		super(bus, entityId);
		this.rotationThrottle = new Throttle(ROTATION_DELAY_MILLIS);
		this.stateManager = stateManager;
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		this.rotationThrottle.tick(elapsedMillis);
		
		Throttle stateToggleThrottle = this.stateManager.getStateToggleThrottle();
		
		if(input.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			if(stateToggleThrottle.getMillisUntilExecution() == 0) {
				this.stateManager.changeState(new MenuState());
			}
		} else {
			stateToggleThrottle.unthrottle();
		}

		if(input.isKeyInputActive(KeyEvent.VK_LEFT)) {
			if(this.rotationThrottle.getMillisUntilExecution() == 0) {
				this.bus.send(new RotationChange(-ROTATION_INCREMENT_DEGREES), this.getEntityId());
			}
		} else if(input.isKeyInputActive(KeyEvent.VK_RIGHT)) {
			if(this.rotationThrottle.getMillisUntilExecution() == 0) {
				this.bus.send(new RotationChange(ROTATION_INCREMENT_DEGREES), this.getEntityId());
			}
		} else {
			this.rotationThrottle.unthrottle();
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_SPACE)) {
			this.bus.send(new ProjectileLaunch(), this.getEntityId());
		}

	}
	
}
