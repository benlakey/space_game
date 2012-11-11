package org.seattlegamer.spacegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.seattlegamer.spacegame.communication.Command;

public class MenuItem extends RenderableText {
	
	private static final Font MENU_FONT = new Font("Courier", Font.BOLD, 32);

	private boolean isSelected;
	private final Command command;
	
	public MenuItem(String text, Command command) {
		super(text, MENU_FONT);
		this.command = command;
	}
	
	public MenuItem(String text, Command command, boolean selected) {
		super(text, MENU_FONT);
		
		this.command = command;
		this.isSelected = selected;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public Command getCommand() {
		return this.command;
	}
	
	@Override
	public void render(Graphics2D graphics) {

		if(this.isSelected) {
			this.setColor(Color.ORANGE);
		} else {
			this.setColor(Color.WHITE);
		}
		
		super.render(graphics);

	}

}
