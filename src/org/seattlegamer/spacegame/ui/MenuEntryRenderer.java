package org.seattlegamer.spacegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.UUID;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.Subscription;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MenuEntryRenderer extends Component {

	private static final Font MENU_FONT = new Font(GameSettings.current().getFont(), Font.BOLD, 64);

	private final int index;
	private final String text;
	private boolean selected;
	private Point position;

	public MenuEntryRenderer(ComponentBus bus, UUID entityId, int index, String text) {
		super(bus, entityId);
		this.index = index;
		this.text = text;
	}
	
	@Subscription
	public void selectionChanged(MenuSelectionChanged changed) {
		this.selected = this.index == changed.getSelectedIndex();
	}

	@Override
	public void render(Graphics2D graphics) {
		
		if(this.position == null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(MENU_FONT);
			Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, MENU_FONT, text);
			this.position = new Point(0, (this.index + 1) * textSize.height);
		}

		graphics.setFont(MENU_FONT);
		if(this.selected) {
			graphics.setColor(Color.RED);
		} else {
			graphics.setColor(Color.WHITE);
		}

		graphics.drawString(text, this.position.x, position.y);

	}

}
