package org.seattlegamer.spacegame.core;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.ProjectileLaunch;
import org.seattlegamer.spacegame.messages.RotationChange;
import org.seattlegamer.spacegame.messages.StateChange;

public class PlayerInput extends Component {

	private static final int ROTATION_DELAY_MILLIS = 10;
	private static final double ROTATION_INCREMENT_DEGREES = 5;
	
	private boolean rotationReady;
	private final Timer rotationThrottle;

	public PlayerInput(Bus bus, UUID entityId) {
		super(bus, entityId);
		this.rotationReady = true;
		this.rotationThrottle = new Timer();
	}

	@Override
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {

		if(keyInput.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			this.bus.broadcast(new StateChange(MenuState.class));
		}

		if(keyInput.isKeyInputActive(KeyEvent.VK_LEFT) && this.rotationReady) {
			this.bus.send(new RotationChange(-ROTATION_INCREMENT_DEGREES), this.getEntityId());
			this.throttleRotation();
		} else if(keyInput.isKeyInputActive(KeyEvent.VK_RIGHT) && this.rotationReady) {
			this.bus.send(new RotationChange(ROTATION_INCREMENT_DEGREES), this.getEntityId());
			this.throttleRotation();
		}
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_SPACE)) {
			this.bus.send(new ProjectileLaunch(), this.getEntityId());
		}

	}
	
	private void throttleRotation() {
		this.rotationReady = false;
		this.rotationThrottle.schedule(new TimerTask() {
			@Override
			public void run() {
				rotationReady = true;
			}
		}, ROTATION_DELAY_MILLIS);
	}
	
}
