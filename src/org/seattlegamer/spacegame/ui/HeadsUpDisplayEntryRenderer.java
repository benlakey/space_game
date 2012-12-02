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
import org.seattlegamer.spacegame.game.PlayerStatusChange;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplayEntryRenderer extends Component {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font HUD_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Color HUD_COLOR = Color.WHITE;

	private final int playerNumber;
	private final String name;
	private int health;
	private boolean needsPositionInitialization;
	
	public HeadsUpDisplayEntryRenderer(Entity entity, int playerNumber, String name) {
		super(entity);
		this.playerNumber = playerNumber;
		this.name = name;
		this.health = 0;
		this.needsPositionInitialization = true;
		this.entity.register(PlayerStatusChange.class, this.getPlayerStatusChangeHandler());
	}

	private Handler<PlayerStatusChange> getPlayerStatusChangeHandler() {
		return new Handler<PlayerStatusChange>() {
			@Override
			public void handle(PlayerStatusChange message) {
				health = message.getHealth();
			}
		};
	}

	@Override
	public void render(Graphics2D graphics, boolean screenSizeChanged) {

		String text = String.format("%s: %d HP", this.name, this.health);

		if(screenSizeChanged) { 
			this.needsPositionInitialization = true;
		}
		
		if(this.needsPositionInitialization) {
			this.initializePosition(graphics.getFontMetrics(HUD_FONT), text);
		}

		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
		Point currentPosition = this.getCurrentPosition(screenSize);

		graphics.setFont(HUD_FONT);
		graphics.setColor(HUD_COLOR);
		graphics.drawString(text, currentPosition.x, currentPosition.y);

	}
	
	private void initializePosition(FontMetrics fontMetrics, String text) {
		
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, HUD_FONT, text);

		Point offset = new Point();
		offset.y = this.playerNumber * (0 - textSize.height);

		this.entity.broadcast(PositionInitialization.class, 
				new PositionInitialization(offset, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM));
		
		this.needsPositionInitialization = false;

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
