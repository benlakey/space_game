package org.seattlegamer.spacegame;

import java.awt.Point;
import java.awt.Rectangle;

public class PositionRetriever {

	private final Entity entity;
	
	public PositionRetriever(Entity entity) {
		this.entity = entity;
	}
	
	public Point getCurrentPosition(Rectangle screenSize) {
		
		PositionQuery query = new PositionQuery(screenSize);
		
		this.entity.broadcast(query);
		
		Point reply = query.getReply();
		if(reply == null) {
			reply = new Point();
		}
		
		return reply;
		
	}
	
}
