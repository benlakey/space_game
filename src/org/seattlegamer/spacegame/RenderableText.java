package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RenderableText implements Renderable {
	
	private String text;
	private Font font;
	private int positionX;
	private int positionY;
	private Color color;

	public RenderableText(String text, Font font) {
		this.text = text;
		this.font = font;
		this.positionX = 0;
		this.positionY = 0;
		this.color = Color.WHITE;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setPositionX(int x) {
		this.positionX = x;
	}
	
	public void setPositionY(int y) {
		this.positionY = y;
	}

	public Font getFont() {
		return this.font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		graphics.setColor(this.color);
		graphics.drawString(this.text, this.positionX, this.positionY);
	}

}
