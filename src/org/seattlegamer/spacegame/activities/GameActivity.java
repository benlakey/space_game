package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.sprites.FilesystemBasedSpriteCache;
import org.seattlegamer.spacegame.sprites.SpriteCache;

public class GameActivity extends Activity {

	@Override
	public void update(long elapsedTimeMillis) {
	}
	
	@Override
	public Iterable<Renderable> getRenderables() {
		SpriteCache cache = FilesystemBasedSpriteCache.get();
		return Arrays.asList(new Renderable[] { cache.getSprite("assets/mars.png") });
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
