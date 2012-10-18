package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class RenderableText implements Renderable {

	private final String text;
	private final Font font;
	private final Point point;

	public RenderableText(String text, Font font) {
		this.text = text;
		this.font = font;
		this.point = new Point();
	}
	
	public void setPosition(int x, int y) {
		this.point.setLocation(x, y);
	}
	
	@Override
	public void render(Graphics2D graphics) {

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		graphics.setColor(Color.WHITE);
		graphics.drawString(this.text, this.point.x, this.point.y);

	}

}
