package org.seattlegamer.spacegame;

import java.awt.Point;
import java.util.UUID;

public class PositionChange extends Message {

	private final Point offset;
	
	public PositionChange(UUID sourceEntityId, Point offset) {
		super(sourceEntityId);
		this.offset = offset;
	}

	public Point getOffset() {
		return this.offset;
	}
	
}
