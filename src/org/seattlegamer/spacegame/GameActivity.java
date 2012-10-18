package org.seattlegamer.spacegame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GameActivity implements Activity {

	@Override
	public Renderable[] getRenderables() {
		URL image = Engine.class.getClassLoader().getResource("mars.png");
		File imageFile = new File(image.getFile());
		BufferedImage planet;
		try {
			planet = ImageIO.read(imageFile);
			Sprite planetSprite = new Sprite(planet);
			Planet mars = new Planet(planetSprite);
			return new Renderable[] { mars.sprite };
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void sendCommand(Command command) {
		// TODO Auto-generated method stub

	}

}
