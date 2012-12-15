package org.seattlegamer.spacegame;

public class RotationChange {

	private final int rotationDegreeDiff;
	
	public RotationChange(int rotationDegreeDiff) {
		this.rotationDegreeDiff = rotationDegreeDiff;
	}

	public int getRotationDegreeDiff() {
		return this.rotationDegreeDiff;
	}
	
}
