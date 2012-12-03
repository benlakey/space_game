package org.seattlegamer.spacegame.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.PositionQuery;

public class Sprite extends Component {

	private Image image;
	//TODO: scale and rotation
	//private float scale;
	//private float rotation;
	
	public Sprite(Bus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.image = image;
		//this.scale = 1.0f;
		//this.rotation = 0;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);
		graphics.drawImage(this.image, currentPosition.x, currentPosition.y, null);
	}
	
	//TODO: this is duplicated in several places. consolidate.
	private Point getCurrentPosition(Rectangle screenSize) {
		
		PositionQuery query = new PositionQuery(screenSize);
		
		this.bus.send(query, this.entityId);
		
		Point reply = query.getReply();
		if(reply == null) {
			reply = new Point();
		}
		
		return reply;

	}

}
