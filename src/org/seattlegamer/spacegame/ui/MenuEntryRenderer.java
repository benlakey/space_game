package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.UUID;

import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.RenderableComponent;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MenuEntryRenderer extends RenderableComponent {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font MENU_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Font MENU_FONT_SELECTED = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
	private static final Color MENU_COLOR = Color.WHITE;
	
	private final UUID idToRenderFor;
	private String text;
	private Font font;
	private int index;
	
	public MenuEntryRenderer(Handler owner, UUID idToRenderFor) {
		super(owner);
		this.idToRenderFor = idToRenderFor;
	}

	@Override
	public void handle(Message message) {
		
		if(message instanceof MenuEntryChange) {
			MenuEntryChange change = (MenuEntryChange)message;
			this.handleMenuEntryChange(change);
		}

	}
	
	private void handleMenuEntryChange(MenuEntryChange change) {
		UUID componentId = change.getComponentId();
		if(this.idToRenderFor.equals(componentId)) {
			this.text = change.getText();
			if(change.isSelected()) {
				this.font = MENU_FONT_SELECTED;
			} else {
				this.font = MENU_FONT;
			}
			this.index = change.getIndex();
		}
	}

	@Override
	public void render(Graphics2D graphics) {

		FontMetrics fontMetrics = graphics.getFontMetrics(this.font);
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, font, text);

		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();

		int centerScreenX = screenSize.width / 2;
		int drawPositionX = centerScreenX - (textSize.width / 2);
		int drawPositionY = textSize.height * (this.index + 1);

		graphics.setFont(this.font);
		graphics.setColor(MENU_COLOR);
		graphics.drawString(this.text, drawPositionX, drawPositionY);

	}

	

}
