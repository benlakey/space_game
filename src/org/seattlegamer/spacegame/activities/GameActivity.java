package org.seattlegamer.spacegame.activities;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.communication.ActivityTransition;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.entities.Planet;
import org.seattlegamer.spacegame.sprites.FilesystemBasedSpriteCache;
import org.seattlegamer.spacegame.sprites.Sprite;
import org.seattlegamer.spacegame.sprites.SpriteCache;

public class GameActivity extends Activity {

	private final Planet planet;
	private final Bus bus;
	
	public GameActivity(Bus bus) {
		this.bus = bus;
		//TODO: this is just here for testing and development. This will be replaced with maps/levels loading whats required.
		SpriteCache cache = FilesystemBasedSpriteCache.get();
		Sprite sprite = cache.getSprite("assets/mars.png");
		this.planet = new Planet(sprite);
	}
	
	@Override
	public void update(long elapsedTimeMillis) {
	}
	
	@Override
	public Iterable<Renderable> getRenderables() {
		return Arrays.asList(new Renderable[] { this.planet.getSprite() });
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ActivityTransition(new MainMenuActivity(this.bus)));
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {

		//TODO: this is just a proof of concept test code.
		
		Sprite sprite = this.planet.getSprite();
		int width = sprite.getWidth();
		int height = sprite.getHeight();
		
		Point point = e.getPoint();
		int x = point.x - (width / 2);
		int y = point.y - (height / 2);
		
		this.planet.getSprite().setPosition(new Point(x, y));

	}

}
