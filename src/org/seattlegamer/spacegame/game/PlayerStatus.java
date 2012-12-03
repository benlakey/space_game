package org.seattlegamer.spacegame.game;

import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Handler;

public class PlayerStatus extends Component {
	
	private static final int START_HEALTH = 100;
	
	private int health;

	public PlayerStatus(Bus bus, UUID entityId) {
		super(bus, entityId);
		this.health = START_HEALTH;
		this.bus.register(PlayerStatusChange.class, this.getPlayerStatusChangeHandler());
		this.bus.broadcast(createPlayerStatusReport());
	}
	
	private Handler<PlayerStatusChange> getPlayerStatusChangeHandler() {
		return new Handler<PlayerStatusChange>() {

			@Override
			public void handle(PlayerStatusChange message) {
				int healthOffset = message.getHealthOffset();
				health += healthOffset;
				bus.broadcast(createPlayerStatusReport());
			}

			@Override
			public boolean canHandleFrom(UUID sourceEntityId) {
				return entityId == sourceEntityId;
			}

		};
	}
	
	private PlayerStatusReport createPlayerStatusReport() {
		PlayerStatusReport playerStatusReport = new PlayerStatusReport(this.entityId);
		playerStatusReport.setHealth(this.health);
		return playerStatusReport;
	}

}
