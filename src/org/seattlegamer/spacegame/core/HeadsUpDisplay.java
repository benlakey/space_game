package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.PlayerStatsReport;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplay extends Component {

	private static final Color HUD_COLOR = Color.WHITE;
	private static final Font HUD_FONT = new Font("Arial", Font.PLAIN, 32);

	private final UUID playerEntityId;
	private final int playerNumber;
	private Point position;
	private String text;
	private int health;

	public HeadsUpDisplay(Bus bus, UUID hudEntityId, UUID playerEntityId, int playerNumber) {
		super(bus, hudEntityId);
		this.playerEntityId = playerEntityId;
		this.playerNumber = playerNumber;
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
			FontMetrics fontMetrics = graphics.getFontMetrics(HUD_FONT);
			Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();
			Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, HUD_FONT, text);
			
			int positionY = screenSize.height - (this.playerNumber * textSize.height);
			
			this.position = new Point(0,  positionY);
		}
		
		graphics.setFont(HUD_FONT);
		graphics.setColor(HUD_COLOR);

		graphics.drawString(text, this.position.x, this.position.y);

	}

}
