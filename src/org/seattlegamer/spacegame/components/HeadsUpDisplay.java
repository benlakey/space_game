package org.seattlegamer.spacegame.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import org.seattlegamer.spacegame.RenderableText;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class HeadsUpDisplay extends ComponentBase {

	private static final String HEALTH_REPORT_FORMAT = "%s: \u2665 %d";
	private static final Font HEALTH_REPORT_FONT = new Font(GameSettings.getFont(), Font.PLAIN, 24);
	
	private final Map<String, RenderableText> playerHealths;
	
	public HeadsUpDisplay() {
		this.playerHealths = new HashMap<String, RenderableText>();
	}

	public void updatePlayerHealth(String name, int health) {

		String newText = String.format(HEALTH_REPORT_FORMAT, name, health);

		if(this.playerHealths.containsKey(name)) {
			RenderableText toUpdate = this.playerHealths.get(name);
			toUpdate.setText(newText);
		} else {
			this.playerHealths.put(name, new RenderableText(newText, HEALTH_REPORT_FONT));
		}

	}

	@Override
	public void render(Graphics2D graphics) {
		
		Dimension screenDimension = GraphicsUtils.getCurrentScreenDimension();
		
		int currentRenderableTextY = screenDimension.height;

		for(String name : this.playerHealths.keySet()) {
			
			RenderableText renderableText = this.playerHealths.get(name);
			
			Dimension renderableTextSize = GraphicsUtils.measureTextPixels(graphics, HEALTH_REPORT_FONT, renderableText.getText());
			currentRenderableTextY -= renderableTextSize.height;

			renderableText.getPosition().y = currentRenderableTextY;

			renderableText.render(graphics);
			
		}
		
	}

}
