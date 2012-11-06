package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import org.seattlegamer.spacegame.Renderable;
import org.seattlegamer.spacegame.commands.ActivityTransitionCommand;
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
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.notifyListeners(new ActivityTransitionCommand(new MainMenuActivity()));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
