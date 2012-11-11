package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.MenuItem;
import org.seattlegamer.spacegame.communication.ActivityTransition;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ExitGame;

public class MainMenuActivity extends MenuActivity {
	
	public MainMenuActivity(Bus bus) {
		super(bus);

		this.menuItems.add(new MenuItem("New Game", new ActivityTransition(new NamingMenuActivity(this.bus))));
		this.menuItems.add(new MenuItem("Credits", null));
		this.menuItems.add(new MenuItem("Exit", new ExitGame(0)));
		
		this.menuItems.get(0).setSelected(true);
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
