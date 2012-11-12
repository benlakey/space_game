package org.seattlegamer.spacegame.components;

import java.awt.Color;
import java.awt.Font;

import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.config.GameSettings;

public class MenuItem extends ComponentBase {
	
	private static final Font MENU_FONT = new Font(GameSettings.getFont(), Font.BOLD, 32);

	private final Command command;
	private final RenderableText renderableText;
	
	public MenuItem(String text) {
		this.renderableText = new RenderableText(text, MENU_FONT);
		this.command = null;
		this.enableInput(false);
	}
	
	public MenuItem(String text, Command command) {
		this.renderableText = new RenderableText(text, MENU_FONT);
		this.command = command;
		this.enableInput(false);
	}

	public void setSelected(boolean isSelected) {
		this.enableInput(isSelected);
		if(isSelected) {
			this.renderableText.setColor(Color.ORANGE);
		} else {
			this.renderableText.setColor(Color.WHITE);
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
