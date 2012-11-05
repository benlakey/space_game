package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class GameActivity extends Activity {

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
