package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;

import org.seattlegamer.spacegame.communication.Command;

public class MenuItem {
	
	private static final Font MENU_FONT = new Font("Courier", Font.BOLD, 32);

	private final Command command;
	private final RenderableText renderableText;
	
	public MenuItem(String text, Command command) {
		this.renderableText = new RenderableText(text, MENU_FONT);
		this.command = command;
	}

	public void setSelected(boolean isSelected) {
		if(isSelected) {
			this.getRenderableText().setColor(Color.ORANGE);
		} else {
			this.getRenderableText().setColor(Color.WHITE);
		}
	}
	
	public Command getCommand() {
		return this.command;
	}

	public RenderableText getRenderableText() {
		return this.renderableText;
	}
	
	public String getText() {
		return this.renderableText.getText();
	}
	
	public void setText(String text) {
		this.renderableText.setText(text);
	}

}
