package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.ComponentBus;
import org.seattlegamer.spacegame.core.Subscription;
import org.seattlegamer.spacegame.messages.PlayerStatsReport;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplayEntryRenderer extends Component {

	private static final Color HUD_COLOR = Color.WHITE;

	private final UUID playerEntityId;
	private final int playerNumber;
	private final Font font;
	private Point position;
	private String text;
	private int health;

	public HeadsUpDisplayEntryRenderer(ComponentBus bus, UUID entityId, UUID playerEntityId, int playerNumber, Font font) {
		super(bus, entityId);
		this.playerEntityId = playerEntityId;
		this.playerNumber = playerNumber;
		this.font = font;
		this.health = 0;
	}
	
	@Subscription
	public void update(PlayerStatsReport report) {
		if(!this.playerEntityId.equals(report.getPlayerEntityId())) {
			return;
		}
		this.text = report.getName();
		this.health = report.getHealth();
	}

	@Override
	public void render(Graphics2D graphics) {

		String text = String.format("%s: %d HP", this.text, this.health);

		if(this.position == null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(this.font);
			Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
			Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, this.font, text);
			
			int positionY = screenSize.height - (this.playerNumber * textSize.height);
			
			this.position = new Point(0,  positionY);
		}
		
		graphics.setFont(this.font);
		graphics.setColor(HUD_COLOR);

		graphics.drawString(text, this.position.x, this.position.y);

	}

}
