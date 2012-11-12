package org.seattlegamer.spacegame.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class TextFieldMenuItem extends MenuItem {

	//TODO: font controlled from properties. control all fonts app-wide.
	private static final Font MENU_FONT = new Font("Courier", Font.BOLD, 32);
	
	private final RenderableText inputText;
	
	public TextFieldMenuItem(String labelText, String inputText) {
		super(labelText);
		this.inputText = new RenderableText(inputText, MENU_FONT);
	}
	
	public String getInputText() {
		return this.inputText.getText();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			
			String text = this.inputText.getText();
			if(text.equals("")) {
				return;
			}

			String newText = text.substring(0, text.length() - 1);
			this.inputText.setText(newText);

		}
		
		super.keyPressed(e);

	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		char keyChar = e.getKeyChar();

		if(Character.isLetterOrDigit(keyChar) || e.getKeyCode() == KeyEvent.VK_SPACE) {
			String text = this.inputText.getText();
			this.inputText.setText(text + keyChar);
		}
		
		super.keyTyped(e);

	}
	
	@Override
	public void render(Graphics2D graphics) {
		
		RenderableText label = this.getRenderableText();
		Point labelPosition = label.getPosition();
		Dimension labelSize = GraphicsUtils.measureTextPixels(graphics, label.getFont(), label.getText());
		
		int inputTextX = labelPosition.x + labelSize.width;
		int inputTextY = labelPosition.y;
		
		this.inputText.setPosition(new Point(inputTextX, inputTextY));
		this.inputText.render(graphics);
		
		super.render(graphics);
		
	}

}
