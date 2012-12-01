package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MenuEntryRenderer extends Component {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font MENU_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Font MENU_FONT_SELECTED = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
	private static final Color MENU_COLOR = Color.WHITE;

	private final int index;
	private final String text;
	private Font font;

	public MenuEntryRenderer(Entity owner, int index, String text) {
		super(owner);
		this.index = index;
		this.text = text;
		this.font = MENU_FONT;
		this.entity.registerHandler(MenuEntryChange.class, this.getMenuEntryChangeHandler());
	}

	private Handler<MenuEntryChange> getMenuEntryChangeHandler() {
		return new Handler<MenuEntryChange>() {
			@Override
			public void handle(MenuEntryChange message) {
				if(index == message.getIndex()) {
					font = MENU_FONT_SELECTED;
				} else {
					font = MENU_FONT;
				}
			}
		};
	}

	@Override
	public void render(Graphics2D graphics) {

		FontMetrics fontMetrics = graphics.getFontMetrics(this.font);
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, this.font, this.text);

		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();

		int centerScreenX = screenSize.width / 2;
		int drawPositionX = centerScreenX - (textSize.width / 2);
		int drawPositionY = textSize.height * (this.index + 1);

		graphics.setFont(this.font);
		graphics.setColor(MENU_COLOR);
		graphics.drawString(this.text, drawPositionX, drawPositionY);

	}

	

}
