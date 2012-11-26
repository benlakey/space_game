package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.utils.Throttle;

public class StateControlInput extends Component {
	
	private static final long KEY_DELAY = 300;

	private static final Throttle exitStateThrottle = new Throttle(KEY_DELAY);
	
	public StateControlInput(Handler owner) {
		super(owner);
	}

	@Override
	public void update(Input input, long elapsedMillis) {
		
		checkThrottledInput(input, KeyEvent.VK_ESCAPE, elapsedMillis, exitStateThrottle, new ExitStateCommand());
		
	}
	
	//TODO: this was duplicated in part from MenuInput. Need to encapsulate this somewhere.
	private void checkThrottledInput(Input input, int inputCode, long elapsedMillis, Throttle throttle, Message message) {
		if(input.isKeyInputActive(inputCode)) {
			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();
			if(remaining == 0) {
				this.owner.handle(message);
				throttle.throttle();
			}
		} else {
			throttle.unthrottle();
		}
	}
	
}
