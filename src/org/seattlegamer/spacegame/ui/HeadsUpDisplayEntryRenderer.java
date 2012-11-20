package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;
import org.seattlegamer.spacegame.RenderableComponent;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.game.PlayerStatusChange;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplayEntryRenderer extends RenderableComponent {

	private static final int FONT_SIZE = 32;
	private static final String FONT_NAME = GameSettings.getFont();
	private static final Font HUD_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
	private static final Color HUD_COLOR = Color.WHITE;

	private int playerNumber;
	private String name;
	private int health;
	
	public HeadsUpDisplayEntryRenderer(Handler owner, int playerNumber, String name) {
		super(owner);
		this.playerNumber = playerNumber;
		this.name = name;
		this.health = 0;
	}

	@Override
	public void handle(Message message) {
		
		if(message instanceof PlayerStatusChange) {
			PlayerStatusChange change = (PlayerStatusChange)message;
			this.handlePlayerStatusChange(change);
		}
		
	}

	private void handlePlayerStatusChange(PlayerStatusChange change) {
		this.health = change.getHealth(); 
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
