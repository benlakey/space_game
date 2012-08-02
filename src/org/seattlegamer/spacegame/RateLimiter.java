package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;

public class RateLimiter {

	private static final Logger logger = Logger.getLogger(RateLimiter.class);
	
	private long lastTimestampMillis;
	private final long delayMillis;
	private final Object timestampLock = new Object();
	
	public RateLimiter(long targetRatePerSecond) {
		this.delayMillis = 1000 / targetRatePerSecond;
	}

	public void blockAsNeeded(long currentTimestampMillis) {

		long toBlockForMillis = this.calculateBlockMillis(currentTimestampMillis);
		
		try {
			Thread.sleep(toBlockForMillis);
		} catch (InterruptedException e) {
			logger.warn("Something interrupted the rate limiter.");
		}
		
		synchronized (this.timestampLock) {
			this.lastTimestampMillis = System.currentTimeMillis();
		}
	}
	
	private long calculateBlockMillis(long currentTimestampMillis) {

		Long limitTimestampMillis = this.calculateLimitTimestampMillis(currentTimestampMillis);
		Long delta = limitTimestampMillis - currentTimestampMillis;

		return NumberUtil.<Long>clamp(delta, 0L, this.delayMillis);
	}
	
	private long calculateLimitTimestampMillis(long currentTimestampMillis) {
		
		synchronized (this.timestampLock) {
			
			if(this.lastTimestampMillis == 0) {
				return currentTimestampMillis;
			}
	
			return this.lastTimestampMillis + this.delayMillis;
			
		}
	}

}
