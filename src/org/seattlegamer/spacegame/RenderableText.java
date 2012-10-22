package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class RenderableText implements Renderable {

	private final String text;
	private final Font font;
	private int positionX;
	private int positionY;
	private boolean centered;

	public RenderableText(String text, Font font) {
		this.text = text;
		this.font = font;
		this.positionX = 0;
		this.positionY = 0;
	}
	
	public void setPositionX(int x) {
		this.positionX = x;
		this.centered = false;
	}
	
	public void setPositionY(int y) {
		this.positionY = y;
	}
	
	public void center() {
		this.positionX = 0;
		this.centered = true;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		
		int posX = this.positionX;

		if(this.centered) {
			posX = this.determineCenteredX(graphics);
		}

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		graphics.setColor(Color.WHITE);
		graphics.drawString(this.text, posX, this.positionY);

	}
	
	private int determineCenteredX(Graphics2D graphics) {
		int centerScreenX = GraphicsUtils.getCenterScreenX();
		int textPixelWidth = GraphicsUtils.getTextPixelWidth(graphics, this.font, this.text);
		double offset = textPixelWidth / 2;
		return (int) (centerScreenX - offset);
	}


}
