package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

public class PositionQuery extends Message {

	private final Rectangle screenSize;
	private Point reply;
	
	public PositionQuery(UUID sourceEntityId, Rectangle screenSize) {
		super(sourceEntityId);
		this.screenSize = screenSize;
	}
	
	public Rectangle getScreenSize() {
		return this.screenSize;
	}

	public Point getReply() {
		return this.reply;
	}

	public void setReply(Point reply) {
		this.reply = reply;
	}

}
