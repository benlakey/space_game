package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.seattlegamer.spacegame.utils.GraphicsUtils;

//TODO: this entire class is just here for testing. 
//I needed some primitives to draw with to play around with sprite transforms.
//Can probably replace with whatever our planet generation strategy is.
public final class TestImageCreator {
	
	private static final int PIXEL_PADDING_DIVISOR = 6;
	
	private final int scale;
	
	public TestImageCreator(int scale) {
		this.scale = scale;
	}
	
	public Image buildPlayer(Color color) {

		int size = 400;
		int buffer = size / PIXEL_PADDING_DIVISOR;
		
		int planetCircumference = size - (2 * buffer);
		int planetWidth = planetCircumference;
		int planetHeight = planetCircumference;
		int planetX = buffer;
		int planetY = buffer;
		
		int gunWidth = buffer;
		int gunHeight = buffer * 2;
		int gunX = (size / 2) - (buffer / 2);
		int gunY = 0;
		
		BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = newImage.createGraphics();
        try {

        	graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        	graphics.setColor(color);
	        graphics.fillOval(planetX, planetY, planetWidth, planetHeight);

	        graphics.setColor(Color.WHITE);
	        graphics.fillRect(gunX, gunY, gunWidth, gunHeight);

        } finally {
        	graphics.dispose();
        }
        
        return GraphicsUtils.getScaledImage(newImage, this.scale);

	}
	
	public static BufferedImage buildAsteroid(Color color) {

		int size = 400;
		int buffer = size / PIXEL_PADDING_DIVISOR;
		
		int planetCircumference = size - (2 * buffer);
		int planetWidth = planetCircumference;
		int planetHeight = planetCircumference;
		int planetX = buffer;
		int planetY = buffer;

		BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = newImage.createGraphics();
        try {

        	graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        	graphics.setColor(color);
	        graphics.fillOval(planetX, planetY, planetWidth, planetHeight);

        } finally {
        	graphics.dispose();
        }
        
        return newImage;

	}
	
}
