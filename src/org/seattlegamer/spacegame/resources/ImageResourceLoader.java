package org.seattlegamer.spacegame.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class ImageResourceLoader implements ResourceLoader<Image> {

	@Override
	public Image load(String name) throws IOException {

		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(name);
		if(stream == null) {
			throw new FileNotFoundException(String.format("Unable to load asset '%s'", name));
		}
		
		BufferedImage bufferedImage = ImageIO.read(stream);
		Image scaledImage = GraphicsUtils.getScaledImage(bufferedImage, GameSettings.current().getScale());

		return GraphicsUtils.toAcceleratedImage(scaledImage);

	}

	@Override
	public Class<Image> getResourceType() {
		return Image.class;
	}

}
