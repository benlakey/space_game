package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;
import org.seattlegamer.spacegame.PositionInitialization;
import org.seattlegamer.spacegame.PositionQuery;
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
	private boolean needsPositionInitialization;

	public MenuEntryRenderer(Entity owner, int index, String text) {
		super(owner);
		this.index = index;
		this.text = text;
		this.font = MENU_FONT;
		this.needsPositionInitialization = true;
		this.entity.register(MenuEntryChange.class, this.getMenuEntryChangeHandler());
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
	public void render(Graphics2D graphics, boolean screenSizeChanged) {

		if(screenSizeChanged) {
			this.needsPositionInitialization = true;
		}

		if(this.needsPositionInitialization) {
			this.initializePosition(graphics.getFontMetrics(this.font), this.text);
		}

		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);

		graphics.setFont(this.font);
		graphics.setColor(MENU_COLOR);
		graphics.drawString(text, currentPosition.x, currentPosition.y);

	}
	
	private void initializePosition(FontMetrics fontMetrics, String text) {
		
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, this.font, text);

		Point offset = new Point();

		offset.x = 0 - (textSize.width / 2);
		offset.y = textSize.height * (this.index + 1);

		this.entity.broadcast(PositionInitialization.class, 
				new PositionInitialization(offset, HorizontalAlignment.CENTER, VerticalAlignment.TOP));

	}

	private Point getCurrentPosition(Rectangle screenSize) {
		
		PositionQuery query = new PositionQuery(screenSize);
		
		this.entity.broadcast(PositionQuery.class, query);
		
		Point reply = query.getReply();
		if(reply == null) {
			reply = new Point();
		}
		
		return reply;
		
	}

	

}
