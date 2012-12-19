package org.seattlegamer.spacegame.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class MenuEntryRenderer extends Component {

	private final int index;
	private final String text;
	private boolean selected;
	private Point position;
	private final Font font;

	public MenuEntryRenderer(Bus bus, UUID entityId, int index, String text, Font font) {
		super(bus, entityId);
		this.index = index;
		this.text = text;
		this.font = font;
	}
	
	@Subscription
	public void selectionChanged(MenuSelectionChanged changed) {
		this.selected = this.index == changed.getSelectedIndex();
	}

	@Override
	public void render(Graphics2D graphics) {
		
		if(this.position == null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(this.font);
			Dimension textSize = GraphicsUtils.measureTextPixels(fontMetrics, this.font, text);
			this.position = new Point(0, (this.index + 1) * textSize.height);
		}

		graphics.setFont(this.font);
		if(this.selected) {
			graphics.setColor(Color.RED);
		} else {
			graphics.setColor(Color.WHITE);
		}

		graphics.drawString(text, this.position.x, position.y);

	}

}
