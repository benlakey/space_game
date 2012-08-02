package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public class Sprite implements Renderable {

	private Point position;
	private final Image image;

	public Sprite(Image image) {
		this.image = image;
		this.position = new Point(0, 0);
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}

	public void render(Graphics2D graphics) {
		graphics.drawImage(this.image, this.position.x, this.position.y, null);
	}	
}
