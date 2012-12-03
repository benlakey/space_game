package org.seattlegamer.spacegame.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class GraphicsUtils {
	
	public static Dimension measureTextPixels(FontMetrics metrics, Font font, String text) {
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		return new Dimension(width, height);
	}

	public static Image bufferedImageToAcceleratedImage(BufferedImage bufferedImage) {
		
		GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
			
		Image acceleratedImage = graphicsConfiguration.createCompatibleImage(
				bufferedImage.getWidth(), 
				bufferedImage.getHeight(),
				Transparency.BITMASK);
		
		Graphics graphicsForAcceleratedImage = acceleratedImage.getGraphics();
		graphicsForAcceleratedImage.drawImage(bufferedImage, 0, 0, null);
		
		return acceleratedImage;
	
	}
	
	public static BufferedImage getScaledImage(BufferedImage original, int scale) {

		int aspectRatio = original.getWidth() / original.getHeight();
		int targetWidth = scale;
		int targetHeight = scale / aspectRatio;
        
		BufferedImage newImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = null;
        try {
			graphics = newImage.createGraphics();
	        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        } finally {
        	graphics.dispose();
        }
        
        return newImage;

    }

}
