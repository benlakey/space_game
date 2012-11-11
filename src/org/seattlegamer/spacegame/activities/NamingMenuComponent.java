package org.seattlegamer.spacegame.activities;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.MenuItem;
import org.seattlegamer.spacegame.communication.ComponentTransition;
import org.seattlegamer.spacegame.communication.Bus;
import org.seattlegamer.spacegame.communication.NewGame;

public class NamingMenuComponent extends MenuComponent {

	private NewGame newGameCommand;

	public NamingMenuComponent(Bus bus) {
		super(bus);
		
		//TODO: accept map choice
		this.newGameCommand = new NewGame("test");
		
		this.menuItems.add(new MenuItem("Player1", null));
		this.menuItems.add(new MenuItem("Player2", null));
		//TODO: allow choice of planet
		this.menuItems.add(new MenuItem("Start!", newGameCommand));
		
		this.menuItems.get(0).setSelected(true);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.bus.send(new ComponentTransition(new MainMenuComponent(this.bus)));
			return;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(this.nameChoiceMenuItemIsSelected()) {
				return;
			}

			if(!this.namesAreValid()) {
				return;
			}
			
		} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			MenuItem menuItem = this.menuItems.get(this.selectedIndex);
			String text = menuItem.getText();
			if(text.equals("")) {
				return;
			}
			String newText = text.substring(0, text.length() - 1);
			menuItem.setText(newText);
		}
		
		super.keyPressed(e);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

		if(!this.nameChoiceMenuItemIsSelected()) {
			return;
		}

		MenuItem menuItem = this.menuItems.get(this.selectedIndex);
		String text = menuItem.getText();
		
		char keyChar = e.getKeyChar();

		if(Character.isLetterOrDigit(keyChar) || e.getKeyCode() == KeyEvent.VK_SPACE) {
			menuItem.setText(text + keyChar);
		}

		this.newGameCommand.setPlayer1Name(this.menuItems.get(0).getText());
		this.newGameCommand.setPlayer2Name(this.menuItems.get(1).getText());
		
		super.keyTyped(e);

	}

	private boolean nameChoiceMenuItemIsSelected() {
		return this.selectedIndex == 0 || this.selectedIndex == 1;
	}
	
	private boolean namesAreValid() {
		return this.hasValidPlayerNameAtMenuIndex(0) && 
				this.hasValidPlayerNameAtMenuIndex(1);
	}
	
	private boolean hasValidPlayerNameAtMenuIndex(int index) {
		MenuItem menuItem = this.menuItems.get(index);
		String playerName = menuItem.getText();
		//TODO: better validation
		return !playerName.equals("");
	}

}
