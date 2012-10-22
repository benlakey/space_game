package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RenderableText implements Renderable {

	private final String text;
	private final Font font;
	private int positionX;
	private int positionY;

	public RenderableText(String text, Font font) {
		this.text = text;
		this.font = font;
		this.positionX = 0;
		this.positionY = 0;
	}
	
	public void setPositionX(int x) {
		this.positionX = x;
	}
	
	public void setPositionY(int y) {
		this.positionY = y;
	}
	
	public void center() {
		//TODO:
//		Dimension currentScreenDimension = GraphicsUtils.getCurrentScreenDimension();
//		
//		FontRenderContext context = gfx2d.getFontRenderContext();
//        Rectangle2D bounds2D = _font.getStringBounds(_widestString, context);
//        Rectangle bounds = bounds2D.getBounds();
	}
	
	@Override
	public void render(Graphics2D graphics) {

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setFont(this.font);
		graphics.setColor(Color.WHITE);
		graphics.drawString(this.text, this.positionX, this.positionY);

	}

}
