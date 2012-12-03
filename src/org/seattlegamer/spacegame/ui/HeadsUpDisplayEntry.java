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
import org.seattlegamer.spacegame.game.PlayerStatusReport;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplayEntry extends Component {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.current().getFont();
	private static final Font HUD_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Color HUD_COLOR = Color.WHITE;

	private final UUID playerEntityId;
	private final int playerNumber;
	private final String name;
	private int health;
	private boolean needsPositionInitialization;
	
	public HeadsUpDisplayEntry(Bus bus, UUID entityId, UUID playerEntityId, int playerNumber, String name) {
		super(bus, entityId);
		this.playerEntityId = playerEntityId;
		this.playerNumber = playerNumber;
		this.name = name;
		this.health = 0;
		this.needsPositionInitialization = true;
		this.bus.register(PlayerStatusReport.class, this.getPlayerStatusReportHandler());
	}

	private Handler<PlayerStatusReport> getPlayerStatusReportHandler() {
		return new Handler<PlayerStatusReport>() {

			@Override
			public void handle(PlayerStatusReport message) {
				if(playerEntityId.equals(message.getPlayerEntityId())) {
					health = message.getHealth();
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

		String text = String.format("%s: %d HP", this.name, this.health);

		if(this.needsPositionInitialization) {
			this.initializePosition(graphics.getFontMetrics(HUD_FONT), text);
			this.needsPositionInitialization = false;
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

		this.bus.send(new PositionInitialization(offset, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM), this.entityId);

	}
	
	//TODO: this is duplicated in several places. consolidate.
	private Point getCurrentPosition(Rectangle screenSize) {
		
		PositionQuery query = new PositionQuery(screenSize);
		
		this.bus.send(query, this.entityId);
		
		Point reply = query.getReply();
		if(reply == null) {
			reply = new Point();
		}
		
		return reply;

	}

}
