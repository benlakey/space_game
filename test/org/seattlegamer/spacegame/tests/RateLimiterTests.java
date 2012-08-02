package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.seattlegamer.spacegame.RateLimiter;

public class RateLimiterTests {

	@Test
	public void rateLimiterBlocksForSpecifiedTimeAtLeast() throws InterruptedException {

		final int targetRate = 100;
		final long delayMillis = 1000 / targetRate;
		
		RateLimiter rateLimiter = new RateLimiter(targetRate);
		rateLimiter.blockAsNeeded(0);

		long timestampMillisStart = System.currentTimeMillis();
		rateLimiter.blockAsNeeded(System.currentTimeMillis());
		long timestampMillisEnd = System.currentTimeMillis();

		long elapsed = timestampMillisEnd - timestampMillisStart;

		assertTrue(elapsed >= delayMillis);

	}
	
}
