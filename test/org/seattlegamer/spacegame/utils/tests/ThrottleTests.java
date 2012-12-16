package org.seattlegamer.spacegame.utils.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.seattlegamer.spacegame.utils.Throttle;

public class ThrottleTests {

	@Test
	public void insufficientTimeThrottles() {
		Throttle throttle = new Throttle(500);
		throttle.throttle();
		throttle.tick(499);
		long timeRemaining = throttle.getMillisUntilExecution();
		assertEquals(1, timeRemaining);
	}
	
	@Test
	public void sufficientTimeDoesntThrottle() {
		Throttle throttle = new Throttle(500);
		throttle.throttle();
		throttle.tick(500);
		long timeRemaining = throttle.getMillisUntilExecution();
		assertEquals(0, timeRemaining);
	}
	
}
