package org.seattlegamer.spacegame.components;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.MenuItem;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.ExitGame;

public class MainMenuComponent extends MenuComponent {
	
	public MainMenuComponent(Bus bus) {
		super(bus);

		this.menuItems.add(new MenuItem("New Game", new ComponentTransition(new NamingMenuComponent(this.bus))));
		this.menuItems.add(new MenuItem("Credits", null));
		this.menuItems.add(new MenuItem("Exit", new ExitGame()));
		
		this.menuItems.get(0).setSelected(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ExitGame());
			return;
		}
		
		super.keyPressed(e);
		
	}

}
