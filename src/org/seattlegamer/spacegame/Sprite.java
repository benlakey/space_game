package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.UUID;

public class Sprite extends Component {

	private final Point position;
	private final Image image;
	private double angleRadians;
	private AffineTransform transform;
	
	public Sprite(ComponentBus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.position = new Point();
		this.image = image;
		this.transform = new AffineTransform();
		this.adjustTransform();
	}
	
	@Subscription
	public void setPosition(SpritePositioning positioning) {
		this.position.x = positioning.getPosition().x;
		this.position.y = positioning.getPosition().y;
		this.adjustTransform();
	}
	
	@Subscription
	public void adjustRotation(RotationChange rotationChange) {
		this.angleRadians += Math.toRadians(rotationChange.getRotationDegreeDiff());
		this.adjustTransform();
	}
	
	private void adjustTransform() {

		this.transform = new AffineTransform();
		
		this.transform.setToTranslation(this.position.x, this.position.y);

		this.transform.rotate(
	    		this.angleRadians, 
	    		this.image.getWidth(null) / 2,
		        this.image.getHeight(null) / 2);
		
	}
	
	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(this.image, this.transform, null);
	}

}
