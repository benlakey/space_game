package org.seattlegamer.spacegame.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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

}
