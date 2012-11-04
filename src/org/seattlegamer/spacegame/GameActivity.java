package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
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
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
