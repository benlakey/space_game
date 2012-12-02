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
import org.seattlegamer.spacegame.PositionRetriever;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.game.PlayerStatusReport;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplay extends Component {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font HUD_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Color HUD_COLOR = Color.WHITE;

	private final int playerNumber;
	private final String name;
	private int health;
	private final PositionRetriever positionRetriever;
	private boolean needsPositionInitialization;
	
	public HeadsUpDisplay(Entity entity, int playerNumber, String name) {
		super(entity);
		this.playerNumber = playerNumber;
		this.name = name;
		this.health = 0;
		this.positionRetriever = new PositionRetriever(entity);
		this.needsPositionInitialization = true;
		this.entity.register(PlayerStatusReport.class, this.getPlayerStatusReportHandler());
	}

	private Handler<PlayerStatusReport> getPlayerStatusReportHandler() {
		return new Handler<PlayerStatusReport>() {
			@Override
			public void handle(PlayerStatusReport message) {
				health = message.getHealth();
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
		Point currentPosition = this.positionRetriever.getCurrentPosition(screenSize);

		graphics.setFont(HUD_FONT);
		graphics.setColor(HUD_COLOR);
		graphics.drawString(text, currentPosition.x, currentPosition.y);

	}
	
	private void initializePosition(FontMetrics fontMetrics, String text) {
		
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, HUD_FONT, text);

		Point offset = new Point();
		offset.y = this.playerNumber * (0 - textSize.height);

		this.entity.broadcast(
				new PositionInitialization(offset, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM));

	}

}
