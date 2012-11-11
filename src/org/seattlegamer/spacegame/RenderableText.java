package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class RenderableText implements Renderable {
	
	private String text;
	private Font font;
	private Point position;
	private Color color;

	public RenderableText(String text, Font font) {
		this.text = text;
		this.font = font;
		this.position = new Point(0, 0);
		this.color = Color.WHITE;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}

	public Font getFont() {
		return this.font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void render(Graphics2D graphics) {

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		graphics.setColor(this.color);
		graphics.drawString(this.text, this.position.x, this.position.y);

	}

}
