package org.seattlegamer.spacegame;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class SpriteCache {
	
	private static Logger logger = Logger.getLogger(SpriteCache.class);
	private static SpriteCache cache = new SpriteCache();
	private static final Object cacheLock = new Object();
	
	public static SpriteCache get() {
		return cache;
	}
	
	private HashMap<String, Sprite> sprites;
	
	public SpriteCache() {
		this.sprites = new HashMap<String, Sprite>();
	}
	
	public Sprite getSprite(String key) throws IOException {
		
		synchronized (cacheLock) {
			
			if(this.sprites.containsKey(key)) {
				
				Object retrieved = this.sprites.get(key);
				if(retrieved instanceof Sprite) {
					return (Sprite)retrieved;
				}

			}
			
			Image image = loadImageFromFilesystem(key);
			Sprite sprite = cacheImageAsSprite(key, image);
			
			return sprite;
		}
		
	}
	
	private Image loadImageFromFilesystem(String key) throws IOException {

		logger.info(String.format("Cache miss. Loading '%s' from filesystem.", key));
		
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(key);
		if(stream == null) {
			throw new FileNotFoundException(String.format("Unable to load asset '%s'", key));
		}
		
		BufferedImage bufferedImage = ImageIO.read(stream);

		return bufferedImageToAcceleratedImage(bufferedImage);

	}
	
	private static Image bufferedImageToAcceleratedImage(BufferedImage bufferedImage) {
		
		GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
			
		Image acceleratedImage = graphicsConfiguration.createCompatibleImage(
				bufferedImage.getWidth(), 
				bufferedImage.getHeight(),
				Transparency.BITMASK);
		
		acceleratedImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
		
		return acceleratedImage;

	}
	
	private Sprite cacheImageAsSprite(String key, Image image) {
		
		Sprite sprite = new Sprite(image);
		this.sprites.put(key, sprite);
		
		return sprite;
		
	}
	
}
