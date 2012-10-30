package org.seattlegamer.spacegame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class GameActivity implements Activity {

	@Override
	public Iterable<Renderable> getRenderables() {
		BufferedImage planet;
		try {
			InputStream stream = Engine.class.getResourceAsStream("/mars.png");
			planet = ImageIO.read(stream); //.read(imageFile);
			Sprite planetSprite = new Sprite(planet);
			Planet mars = new Planet(planetSprite);
			return Arrays.asList(new Renderable[] { mars.sprite });
		} catch (IOException e) {
			//TODO:
		}
		return null;
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return false;
	}

	@Override
	public <T extends Command> void handle(T command) {
	}

}
