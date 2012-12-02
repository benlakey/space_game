package org.seattlegamer.spacegame.game;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;

public class PlayerStatus extends Component {
	
	private static final int START_HEALTH = 100;
	
	private int health;

	public PlayerStatus(Entity entity) {
		super(entity);
		this.health = START_HEALTH;
		this.entity.register(PlayerStatusChange.class, this.getPlayerStatusChangeHandler());
		this.entity.broadcast(createPlayerStatusReport());
	}
	
	private Handler<PlayerStatusChange> getPlayerStatusChangeHandler() {
		return new Handler<PlayerStatusChange>() {
			@Override
			public void handle(PlayerStatusChange message) {
				int healthOffset = message.getHealthOffset();
				health += healthOffset;
				entity.broadcast(createPlayerStatusReport());
			}
		};
	}
	
	private PlayerStatusReport createPlayerStatusReport() {
		PlayerStatusReport playerStatusReport = new PlayerStatusReport();
		playerStatusReport.setHealth(this.health);
		return playerStatusReport;
	}

}
