package org.seattlegamer.spacegame.components;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.HealthUpdate;

public class GameComponent extends ComponentBase {

	private final Bus bus;
	private final Player player1;
	private final Player player2;
	
	public GameComponent(Bus bus, Player player1, Player player2, GameMap gameMap, HeadsUpDisplay headsUpDisplay) {
		this.bus = bus;
		this.player1 = player1;
		this.player2 = player2;
		this.subComponents.add(gameMap);
		this.subComponents.add(headsUpDisplay);
		
		this.initializeHUD();
	}
	
	private void initializeHUD() {
		this.bus.send(new HealthUpdate(player1.getName(), player1.getHealth()));
		this.bus.send(new HealthUpdate(player2.getName(), player2.getHealth()));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ComponentTransition(new MainMenu(this.bus)));
		}

		super.keyPressed(e);
		
	}

}
