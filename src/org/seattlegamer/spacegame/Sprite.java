package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.UUID;

public class Sprite extends Component {

	private final Image image;
	private AffineTransform transform;
	
	public Sprite(ComponentBus bus, UUID entityId, Image image) {
		super(bus, entityId);
		this.image = image;
		this.transform = new AffineTransform();
	}

	@Subscription
	public void updateTransform(AffineTransform transform) {
		this.transform = transform;
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(this.image, this.transform, null);
	}

}
