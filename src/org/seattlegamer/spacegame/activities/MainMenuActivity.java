package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.MenuItem;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ExitGame;
import org.seattlegamer.spacegame.communication.NewGame;

public class MainMenuActivity extends MenuActivity {
	
	public MainMenuActivity(Bus bus) {
		super(bus);

		this.addMenuItem(new MenuItem("New Game", new NewGame("test"), true));
		this.addMenuItem(new MenuItem("Credits", null));
		this.addMenuItem(new MenuItem("Exit", new ExitGame(0)));
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ExitGame(0));
			return;
		}
		
		super.keyPressed(e);
		
	}

}
