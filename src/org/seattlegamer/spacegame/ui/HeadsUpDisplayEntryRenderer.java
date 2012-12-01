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
import org.seattlegamer.spacegame.game.PlayerStatusChange;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplayEntryRenderer extends Component {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font HUD_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Color HUD_COLOR = Color.WHITE;

	private int playerNumber;
	private String name;
	private int health;
	
	public HeadsUpDisplayEntryRenderer(Entity owner, int playerNumber, String name) {
		super(owner);
		this.playerNumber = playerNumber;
		this.name = name;
		this.health = 0;
		this.entity.registerHandler(PlayerStatusChange.class, this.getPlayerStatusChangeHandler());
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
	public void render(Graphics2D graphics) {

		String hudEntry = String.format("%s: %d HP", this.name, this.health);

		FontMetrics fontMetrics = graphics.getFontMetrics(HUD_FONT);
		Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, HUD_FONT, hudEntry);

		Rectangle screenSize = graphics.getDeviceConfiguration().getBounds();

		int drawPositionX = 0;
		int drawPositionY = screenSize.height - (this.playerNumber * textSize.height);

		graphics.setFont(HUD_FONT);
		graphics.setColor(HUD_COLOR);
		graphics.drawString(hudEntry, drawPositionX, drawPositionY);

	}

}
