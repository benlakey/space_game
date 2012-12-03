package org.seattlegamer.spacegame;

import java.awt.Point;
import java.util.UUID;

import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;

public class PositionInitialization extends Message {

	private final Point offset;
	private final HorizontalAlignment horizontalAlignment;
	private final VerticalAlignment verticalAlignment;
	
	public PositionInitialization(
			UUID sourceEntityId, 
			Point offset, 
			HorizontalAlignment horizontalAlignment, 
			VerticalAlignment verticalAlignment) {
		super(sourceEntityId);
		this.offset = offset;
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
	}
	
	public Point getOffset() {
		return offset;
	}

	public HorizontalAlignment getHorizontalAlignment() {
		return this.horizontalAlignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

}
