package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.UUID;

public class Sprite extends Component {

	private final Point position;
	private final Image image;
	
	public Sprite(ComponentBus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.position = new Point();
		this.image = image;
	}
	
	@Subscription
	public void setPosition(SpritePositioning positioning) {
		this.position.x = positioning.getPosition().x;
		this.position.y = positioning.getPosition().y;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(this.image, this.position.x, this.position.y, null);
	}

}
