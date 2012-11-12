package org.seattlegamer.spacegame.components;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.NewGame;

public class NamingMenu extends MenuComponent {

	private final NewGame newGameCommand;
	private final TextFieldMenuItem player1Input;
	private final TextFieldMenuItem player2Input;

	public NamingMenu(Bus bus) {
		super(bus);
		
		//TODO: accept map choice
		this.newGameCommand = new NewGame("test");
		this.player1Input = new TextFieldMenuItem("Player1: ", "");
		this.player2Input = new TextFieldMenuItem("Player2: ", "");
		
		this.addMenuItem(player1Input);
		this.addMenuItem(player2Input);
		this.addMenuItem(new MenuItem("Start!", newGameCommand));
		
		this.selectIndex(0);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ComponentTransition(new MainMenu(this.bus)));
			return;
		}

		super.keyPressed(e);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		this.updateNewGameParameters();
		super.keyTyped(e);
	}
	
	private void updateNewGameParameters() {
		this.newGameCommand.setPlayer1Name(this.player1Input.getInputText());
		this.newGameCommand.setPlayer2Name(this.player2Input.getInputText());
	}

}
