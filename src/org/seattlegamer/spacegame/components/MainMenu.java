package org.seattlegamer.spacegame.components;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.ExitGame;

public class MainMenu extends MenuComponent {
	
	public MainMenu(Bus bus) {
		super(bus);

		this.addMenuItem(new MenuItem("New Game", new ComponentTransition(new NamingMenu(this.bus))));
		this.addMenuItem(new MenuItem("Credits", new ComponentTransition(new CreditsMenu(this.bus))));
		this.addMenuItem(new MenuItem("Exit", new ExitGame()));
		
		this.getIndex(0).setSelected(true);
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
