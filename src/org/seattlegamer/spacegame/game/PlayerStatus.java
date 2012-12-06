package org.seattlegamer.spacegame.game;

import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Handler;

public class PlayerStatus extends Component {
	
	private static final int START_HEALTH = 100;
	
	private int health;
	private final Handler<PlayerStatusChange> playerStatusChangeHandler = this.getPlayerStatusChangeHandler();

	public PlayerStatus(Bus bus, UUID entityId) {
		super(bus, entityId);
		this.health = START_HEALTH;
		
		this.bus.broadcast(new PlayerStatusReport(this.entityId, this.health));
	}
	
	@Override
	public void registerHandlers() {
		this.bus.register(PlayerStatusChange.class, playerStatusChangeHandler);
	}
	
	@Override
	public void deregisterHandlers() {
		this.bus.deregister(PlayerStatusChange.class, playerStatusChangeHandler);
	}
	
	private Handler<PlayerStatusChange> getPlayerStatusChangeHandler() {
		return new Handler<PlayerStatusChange>() {

			@Override
			public void handle(PlayerStatusChange message) {
				int healthOffset = message.getHealthOffset();
				health += healthOffset;
				bus.broadcast(new PlayerStatusReport(entityId, health));
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
			}

		};
	}

}
