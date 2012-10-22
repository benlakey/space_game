package org.seattlegamer.spacegame.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GraphicsUtils {

	public static Dimension getCurrentScreenDimension() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		return toolkit.getScreenSize();
	}
	
	//TODO: works if fullscreen, does not work as intended if windowed.
	public static int getCenterScreenX() {
		Dimension currentScreenDimension = getCurrentScreenDimension();
		double screenWidth = currentScreenDimension.getWidth();
		double centerX = screenWidth / 2;
		return (int) centerX;
	}
	
	public static int getTextPixelWidth(Graphics2D graphics, Font font, String text) {
		FontRenderContext context = graphics.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, context);
		return (int) bounds.getWidth();
	}
	
}
