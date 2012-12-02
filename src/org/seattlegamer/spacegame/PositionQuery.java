package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.Rectangle;

public class PositionQuery implements Message {

	private final Rectangle screenSize;
	private Point reply;
	
	public PositionQuery(Rectangle screenSize) {
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
