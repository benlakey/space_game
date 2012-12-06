package org.seattlegamer.spacegame;

import java.util.HashMap;
import java.util.Map;

import org.seattlegamer.spacegame.utils.Throttle;

public class KeyThrottle {
	
	private static final long KEY_DELAY = 300;
	private static final Map<Integer, Throttle> throttles;

	static {
		throttles = new HashMap<Integer, Throttle>();
	}
	
	public static boolean canExecute(Input input, int inputCode, long elapsedMillis) {
		
		if(!throttles.containsKey(inputCode)) {
			throttles.put(inputCode, new Throttle(KEY_DELAY));
			return true;
		}
		
		Throttle throttle = throttles.get(inputCode);
		
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
