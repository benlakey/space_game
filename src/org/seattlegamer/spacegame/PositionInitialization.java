package org.seattlegamer.spacegame;

import java.awt.Point;

import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;

public class PositionInitialization implements Message {

	private final Point offset;
	private final HorizontalAlignment horizontalAlignment;
	private final VerticalAlignment verticalAlignment;
	
	public PositionInitialization(
			Point offset, 
			HorizontalAlignment horizontalAlignment, 
			VerticalAlignment verticalAlignment) {
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
