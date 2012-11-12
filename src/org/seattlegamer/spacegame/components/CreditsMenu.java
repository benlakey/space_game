package org.seattlegamer.spacegame.components;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;

public class CreditsMenu extends MenuComponent {

	public CreditsMenu(Bus bus) {
		super(bus);
		
		this.addMenuItem(new MenuItem("Programming: Caleb Gossler"));
		this.addMenuItem(new MenuItem("Programming: Ben Lakey"));
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ComponentTransition(new MainMenu(this.bus)));
			return;
		}

		super.keyPressed(e);
		
	}

}
