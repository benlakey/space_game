package org.seattlegamer.spacegame;

public class RotationChange {

	private final double rotationDegreeDiff;
	
	public RotationChange(double rotationDegreeDiff) {
		this.rotationDegreeDiff = rotationDegreeDiff;
	}

	public double getRotationDegreeDiff() {
		return this.rotationDegreeDiff;
	}
	
}
