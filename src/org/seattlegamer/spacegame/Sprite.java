package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.awt.Image;

public class Sprite {

	private final Image image;

	public Sprite(Image image) {
		this.image = image;
	}
	
	public int getWidth() {
		return this.image.getWidth(null);
	}
	
	public int getHeight() {
		return this.image.getHeight(null);
	}
	
	public void draw(Graphics2D graphics, int x, int y) {
		graphics.drawImage(this.image, x, y, null);
	}
	
}
