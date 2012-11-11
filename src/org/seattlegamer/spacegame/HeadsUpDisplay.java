package org.seattlegamer.spacegame;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import org.seattlegamer.spacegame.communication.Command;
import org.seattlegamer.spacegame.communication.Handler;
import org.seattlegamer.spacegame.communication.HealthReport;

public class HeadsUpDisplay implements Handler {

	private static final String HEALTH_REPORT_FORMAT = "%s: %d";
	private static final Font HEALTH_REPORT_FONT = new Font("Courier", Font.BOLD, 32);
	
	private Map<String, RenderableText> playerHealths;
	
	public HeadsUpDisplay() {
		this.playerHealths = new HashMap<String, RenderableText>();
	}

	public Iterable<? extends Renderable> getRenderables() {
		return this.playerHealths.values();
	}

	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof HealthReport;
	}

	@Override
	public void handle(Command command) {
		
		HealthReport healthReport = (HealthReport)command;
		
		String name = healthReport.getName();
		int health = healthReport.getHealth();
		String newText = String.format(HEALTH_REPORT_FORMAT, name, health);

		if(this.playerHealths.containsKey(name)) {
			RenderableText toUpdate = this.playerHealths.get(name);
			toUpdate.setText(newText);
		} else {
			this.playerHealths.put(name, new RenderableText(newText, HEALTH_REPORT_FONT));
		}

	}

}
