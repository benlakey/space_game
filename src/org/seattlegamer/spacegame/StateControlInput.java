package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.utils.Throttle;

public class StateControlInput extends Component {
	
	public StateControlInput(Bus bus, UUID entityId) {
		super(bus, entityId);
	}

	private static final long KEY_DELAY = 300;

	private static final Throttle exitStateThrottle = new Throttle(KEY_DELAY);

	@Override
	public void update(Input input, long elapsedMillis) {
		if(input.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			exitStateThrottle.tick(elapsedMillis);
			long remaining = exitStateThrottle.timeRemaining();
			if(remaining == 0) {
				this.bus.broadcast(new ExitStateCommand(this.entityId));
				exitStateThrottle.throttle();
			}
		} else {
			exitStateThrottle.unthrottle();
		}
	}
	
}
