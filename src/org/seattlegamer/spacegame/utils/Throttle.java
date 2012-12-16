package org.seattlegamer.spacegame.utils;

public class Throttle {
	
	private final int delayMillis;
	private long millisSinceLastExecution;

	public Throttle(int delayMillis) {
		this.delayMillis = delayMillis;
		this.millisSinceLastExecution = delayMillis;
	}

	public void tick(long elapsedMillis) {
		this.millisSinceLastExecution += elapsedMillis;
	}
	
	public long getMillisUntilExecution() {

		long millisRemaining = this.getMillisRemaining();
		if(millisRemaining == 0) {
			this.millisSinceLastExecution = 0;
		}
		
		return millisRemaining;

	}
	
	public void throttle() {
		this.millisSinceLastExecution = 0;
	}
	
	public void unthrottle() {
		this.millisSinceLastExecution = this.delayMillis;
	}
	
	private long getMillisRemaining() {
		long remaining = this.delayMillis - this.millisSinceLastExecution;
		return NumberUtils.<Long>clamp(remaining, 0L, Long.MAX_VALUE);
	}

}
