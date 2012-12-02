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
		checkThrottledInput(input, KeyEvent.VK_ESCAPE, elapsedMillis, exitStateThrottle, ExitStateCommand.class, new ExitStateCommand());
	}
	
	//TODO: this was duplicated in part from MenuInput. Need to encapsulate this somewhere.
	private void checkThrottledInput(Input input, int inputCode, long elapsedMillis, Throttle throttle, Class<?> messageClass, Message message) {
		if(input.isKeyInputActive(inputCode)) {
			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();
			if(remaining == 0) {
				this.entity.broadcast(messageClass, message);
				throttle.throttle();
			}
		} else {
			throttle.unthrottle();
		}
	}
	
}
