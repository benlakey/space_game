package org.seattlegamer.spacegame.sprites;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import org.seattlegamer.spacegame.Renderable;

public class Sprite implements Renderable {

	private Point position;
	private final Image image;

	public Sprite(Image image) {
		this.image = image;
		this.position = new Point(0, 0);
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public int getWidth() {
		//TODO: scaled?
		return this.image.getWidth(null);
	}
	
	public int getHeight() {
		return this.image.getHeight(null);
	}

	public void render(Graphics2D graphics) {
		graphics.drawImage(this.image, this.position.x, this.position.y, null);
	}	
}
