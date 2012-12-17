package org.seattlegamer.spacegame.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class AnimationLoader {
	
	private static final int FRAME_COUNT = 8;
	
	private final Image image;
	private final int frameWidth;
	private final int frameHeight;
	
	public AnimationLoader(Image image) {
		this.image = image;
		this.frameWidth = image.getWidth(null) / FRAME_COUNT;
		this.frameHeight = image.getHeight(null);
	}

	public Image[] getFrames() {

		Image[] frames = new Image[FRAME_COUNT];

		for(int i = 0; i < FRAME_COUNT; i++) {
			frames[i] = this.getFrame(i);
		}
		
		return frames;

	}
	
	private Image getFrame(int index) {
		
		BufferedImage frame = new BufferedImage(this.frameWidth, this.frameHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D graphics = null;
        try {
			graphics = frame.createGraphics();
	        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics.drawImage(this.image, 0, 0, this.frameWidth, this.frameHeight, 
	        		this.frameWidth * index, 0, this.frameWidth * index + this.frameWidth, this.frameHeight, null);
        } finally {
        	graphics.dispose();
        }
        
        return frame;
		
	}
	
}
