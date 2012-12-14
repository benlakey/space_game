package org.seattlegamer.spacegame;

import java.util.HashMap;
import java.util.Map;

import org.seattlegamer.spacegame.utils.Throttle;

public final class KeyThrottle {

	private static final long KEY_DELAY = 300;
	private static final Map<Integer, Throttle> throttles;

	static {
		throttles = new HashMap<Integer, Throttle>();
	}
	
	private KeyThrottle() {}
	
	public static boolean executable(Input input, int inputCode, long elapsedMillis) {
		
		Throttle throttle = throttles.get(inputCode);
		if(throttle == null) {
			throttle = new Throttle(KEY_DELAY);
			throttles.put(inputCode, throttle);
		}
		
		if(input.isKeyInputActive(inputCode)) {

			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();

			if(remaining == 0) {
				throttle.throttle();
				return true;
			}

		} else {
			throttle.unthrottle();
		}

		return false;

	}
	
}
