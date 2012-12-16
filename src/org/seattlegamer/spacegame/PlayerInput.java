package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.utils.Throttle;

public class PlayerInput extends Component {
	
	private static final int ROTATION_DELAY_MILLIS = 10;
	private static final double ROTATION_INCREMENT_DEGREES = 5;

	private final Throttle rotationThrottle;
	
	public PlayerInput(ComponentBus bus, UUID entityId) {
		super(bus, entityId);
		this.rotationThrottle = new Throttle(ROTATION_DELAY_MILLIS);
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		this.rotationThrottle.tick(elapsedMillis);
		
		if(input.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			if(MenuState.menuToggleThrottle.getMillisUntilExecution() == 0) {
				StateManager.setState(new MenuState());
			}
		} else {
			MenuState.menuToggleThrottle.unthrottle();
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
			//TODO: this is just here for fun animation testing
			this.bus.broadcast(new AnimationStart());
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_UP)) {
			this.bus.broadcast(new SpeedChange(0.1));
		}
		if(input.isKeyInputActive(KeyEvent.VK_DOWN)) {
			this.bus.broadcast(new SpeedChange(-0.1));
		}

	}
	
}
