package org.seattlegamer.spacegame;

import java.awt.Point;

public class PositionChange implements Message {

	private final Point offset;
	
	public PositionChange(Point offset) {
		this.offset = offset;
	}

	public Point getOffset() {
		return this.offset;
	}
	
}
