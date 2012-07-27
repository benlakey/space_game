package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.seattlegamer.spacegame.RateLimiter;

public class RateLimiterTests {

	@Test
	public void rateLimiterBlocksForSpecifiedTimeAtLeast() throws InterruptedException {
		
		BasicConfigurator.configure();
		
		final long delayMillis = 400;
		
		RateLimiter rateLimiter = new RateLimiter(delayMillis);
		rateLimiter.blockAsNeeded(0);

		long timestampMillisStart = System.currentTimeMillis();
		rateLimiter.blockAsNeeded(System.currentTimeMillis());
		long timestampMillisEnd = System.currentTimeMillis();

		long elapsed = timestampMillisEnd - timestampMillisStart;

		assertTrue(elapsed >= delayMillis);

	}
	
}
