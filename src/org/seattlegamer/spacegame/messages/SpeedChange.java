package org.seattlegamer.spacegame.messages;

public class SpeedChange {

	private final double speedDiff;
	
	public SpeedChange(double speedDiff) {
		this.speedDiff = speedDiff;
	}

	public double getSpeedDiff() {
		return speedDiff;
	}
	
}
