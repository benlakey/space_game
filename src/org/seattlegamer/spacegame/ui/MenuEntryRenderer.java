package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Position.HorizontalAlignment;
import org.seattlegamer.spacegame.Position.VerticalAlignment;
import org.seattlegamer.spacegame.PositionInitialization;
import org.seattlegamer.spacegame.PositionQuery;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MenuEntryRenderer extends Component {

	private static final int FONT_SIZE = 64;
	private static final String FONT_NAME = GameSettings.current().getFont();
	private static final Font MENU_FONT = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
	private static final Color MENU_COLOR = Color.WHITE;
	private static final Color MENU_COLOR_SELECTED = Color.RED;

	private final int index;
	private final String text;
	private Color color;
	private boolean needsPositionInitialization;
	
	private final Handler<MenuEntryChange> menuEntryChangeHandler = this.getMenuEntryChangeHandler();

	public MenuEntryRenderer(Bus bus, UUID entityId, int index, String text) {
		super(bus, entityId);
		this.index = index;
		this.text = text;
		this.color = MENU_COLOR;
		this.needsPositionInitialization = true;
	}
	
	@Override
	public void registerHandlers() {
		this.bus.register(MenuEntryChange.class, menuEntryChangeHandler);
	}
	
	@Override
	public void deregisterHandlers() {
		this.bus.deregister(MenuEntryChange.class, menuEntryChangeHandler);
	}

	private Handler<MenuEntryChange> getMenuEntryChangeHandler() {
		return new Handler<MenuEntryChange>() {

			@Override
			public void handle(MenuEntryChange message) {
				if(index == message.getIndex()) {
					color = MENU_COLOR_SELECTED;
				} else {
					color = MENU_COLOR;
				}
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
			}

		};
	}

	@Override
	public void render(Graphics2D graphics) {

		if(this.needsPositionInitialization) {
			this.initializePosition(graphics, this.text);
			this.needsPositionInitialization = false;
		}

		graphics.setFont(MENU_FONT);
		graphics.setColor(this.color);
		
		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);
		if(currentPosition == null) {
			currentPosition = new Point();
		}
		graphics.drawString(text, currentPosition.x, currentPosition.y);

	}
	
	private void initializePosition(Graphics2D graphics, String text) {
		
		FontMetrics fontMetrics = graphics.getFontMetrics(MENU_FONT);
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, MENU_FONT, text);

		Point offset = new Point();

		offset.x = 0 - (textSize.width / 2);
		offset.y = textSize.height * (this.index + 1);

		this.bus.send(new PositionInitialization(offset, HorizontalAlignment.CENTER, VerticalAlignment.TOP), this.entityId);

	}

	private Point getCurrentPosition(Rectangle screenSize) {
		PositionQuery query = new PositionQuery(screenSize);
		this.bus.send(query, this.entityId);
		return query.getReply();
	}

}
