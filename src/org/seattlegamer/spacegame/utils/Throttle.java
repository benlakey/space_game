package org.seattlegamer.spacegame.utils;

//TODO: use this instead of RateLimiter?
public class Throttle {

	private long delayMillis;
	private long millisSinceLastExecution;
	
	public Throttle(long delayMillis) {
		this.delayMillis = delayMillis;
		this.millisSinceLastExecution = delayMillis;
	}
	
	public void tick(long elapsedMillis) {
		this.millisSinceLastExecution += elapsedMillis;
	}

	public long timeRemaining() {
		long remaining = this.delayMillis - this.millisSinceLastExecution;
		return NumberUtils.<Long>clamp(remaining, 0L, Long.MAX_VALUE);
	}
	
	public void unthrottle() {
		this.millisSinceLastExecution = this.delayMillis;
	}
	
	public void rethrottle() {
		this.millisSinceLastExecution = 0;
	}
	
}
