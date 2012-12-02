package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.utils.Throttle;

public class StateControlInput extends Component {
	
	private static final long KEY_DELAY = 300;

	private static final Throttle exitStateThrottle = new Throttle(KEY_DELAY);
	
	public StateControlInput(Entity entity) {
		super(entity);
	}

	@Override
	public void update(Input input, long elapsedMillis) {
		if(input.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			exitStateThrottle.tick(elapsedMillis);
			long remaining = exitStateThrottle.timeRemaining();
			if(remaining == 0) {
				this.entity.broadcast(new ExitStateCommand());
				exitStateThrottle.throttle();
			}
		} else {
			exitStateThrottle.unthrottle();
		}
	}
	
}
