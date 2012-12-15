package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.ui.MenuState;
import org.seattlegamer.spacegame.utils.Throttle;

public class PlayerInput extends Component {
	
	private static final int ROTATION_DELAY_MILLIS = 10;
	private final Throttle rotationThrottle;
	
	public PlayerInput(ComponentBus bus, UUID entityId) {
		super(bus, entityId);
		this.rotationThrottle = new Throttle(ROTATION_DELAY_MILLIS);
	}

	@Override
	public void update(Input input, long elapsedMillis) {
		
		if(InterfaceKeyThrottle.executable(input, KeyEvent.VK_ESCAPE, elapsedMillis)) {
			StateManager.setState(new MenuState());
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_LEFT)) {
			this.tryApplyRotation(KeyEvent.VK_LEFT, elapsedMillis);
		} else if(input.isKeyInputActive(KeyEvent.VK_RIGHT)) {
			this.tryApplyRotation(KeyEvent.VK_RIGHT, elapsedMillis);
		} else if(input.isKeyInputActive(KeyEvent.VK_SPACE)) {
			//TODO: this is here just for fun testing for now
			this.bus.broadcast(new AnimationStart()); 
		} else {
			this.rotationThrottle.unthrottle();
		}

	}
	
	private void tryApplyRotation(int keyCode, long elapsedMillis) {
		
		this.rotationThrottle.tick(elapsedMillis);
		long remaining = this.rotationThrottle.timeRemaining();

		if(remaining == 0) {
			this.rotationThrottle.throttle();
			if(keyCode == KeyEvent.VK_LEFT) {
				this.bus.send(new RotationChange(-2), this.getEntityId());
			} else if(keyCode == KeyEvent.VK_RIGHT) {
				this.bus.send(new RotationChange(2), this.getEntityId());
			}
		}
		
	}
	
}
