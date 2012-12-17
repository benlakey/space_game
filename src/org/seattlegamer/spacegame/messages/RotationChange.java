package org.seattlegamer.spacegame.messages;

public class RotationChange {

	private final double rotationDegreeDiff;
	
	public RotationChange(double rotationDegreeDiff) {
		this.rotationDegreeDiff = rotationDegreeDiff;
	}

	public double getRotationDegreeDiff() {
		return this.rotationDegreeDiff;
	}
	
}
