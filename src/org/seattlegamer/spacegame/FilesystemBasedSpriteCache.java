package org.seattlegamer.spacegame;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class FilesystemBasedSpriteCache extends SpriteCache {
	
	private static FilesystemBasedSpriteCache instance = new FilesystemBasedSpriteCache();
	private static Logger logger = Logger.getLogger(FilesystemBasedSpriteCache.class);
	
	public static FilesystemBasedSpriteCache get() {
		return instance;
	}

	private FilesystemBasedSpriteCache() {}
	
	@Override
	protected Sprite handleCacheMiss(String key) {
		
		try {
			Image image = this.loadImageFromFilesystem(key);
			return this.cacheImageAsSprite(key, image);
		} catch (IOException e) {
			logger.warn(String.format("Unable to load sprite with key '%s' from the filesystem.", key));
		}

		return null;
		
	}
	
	private Image loadImageFromFilesystem(String key) throws IOException {

		logger.info(String.format("Cache miss. Loading '%s' from filesystem.", key));
		
		Class<? extends FilesystemBasedSpriteCache> theClass = this.getClass();
		ClassLoader classLoader = theClass.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(key);
		if(stream == null) {
			throw new FileNotFoundException(String.format("Unable to load asset '%s'", key));
		}
		
		BufferedImage bufferedImage = ImageIO.read(stream);

		return bufferedImageToAcceleratedImage(bufferedImage);

	}
	
	private Sprite cacheImageAsSprite(String key, Image image) {
		
		Sprite sprite = new Sprite(image);
		this.sprites.put(key, sprite);
		
		return sprite;
		
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
		
		Graphics graphicsForAcceleratedImage = acceleratedImage.getGraphics();
		graphicsForAcceleratedImage.drawImage(bufferedImage, 0, 0, null);
		
		return acceleratedImage;

	}

}
