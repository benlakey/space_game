package org.seattlegamer.spacegame.core;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.ProjectileLaunch;
import org.seattlegamer.spacegame.messages.RotationChange;
import org.seattlegamer.spacegame.messages.StateChange;
import org.seattlegamer.spacegame.utils.Throttle;

public class PlayerInput extends Component {

	private static final int ROTATION_DELAY_MILLIS = 10;
	private static final double ROTATION_INCREMENT_DEGREES = 5;

	private final Throttle rotationThrottle;
	
	public PlayerInput(Bus bus, UUID entityId) {
		super(bus, entityId);
		this.rotationThrottle = new Throttle(ROTATION_DELAY_MILLIS);
	}

	@Override
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {

		this.rotationThrottle.tick(elapsedMillis);

		if(keyInput.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			this.bus.broadcast(new StateChange(MenuState.class));
		}

		if(keyInput.isKeyInputActive(KeyEvent.VK_LEFT)) {
			if(this.rotationThrottle.getMillisUntilExecution() == 0) {
				this.bus.send(new RotationChange(-ROTATION_INCREMENT_DEGREES), this.getEntityId());
			}
		} else if(keyInput.isKeyInputActive(KeyEvent.VK_RIGHT)) {
			if(this.rotationThrottle.getMillisUntilExecution() == 0) {
				this.bus.send(new RotationChange(ROTATION_INCREMENT_DEGREES), this.getEntityId());
			}
		} else {
			this.rotationThrottle.unthrottle();
		}
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_SPACE)) {
			this.bus.send(new ProjectileLaunch(), this.getEntityId());
		}

	}
	
}
