package org.seattlegamer.spacegame.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.PositionRetriever;

public class Sprite extends Component {

	private Image image;
	//TODO: scale and rotation
	//private float scale;
	//private float rotation;
	private final PositionRetriever positionRetriever;
	
	public Sprite(Entity entity, Image image) {
		super(entity);
		this.image = image;
		//this.scale = 1.0f;
		//this.rotation = 0;
		this.positionRetriever = new PositionRetriever(entity);
	}
	
	@Override
	public void render(Graphics2D graphics) {
		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.positionRetriever.getCurrentPosition(screenSize);
		graphics.drawImage(this.image, currentPosition.x, currentPosition.y, null);
	}

}
