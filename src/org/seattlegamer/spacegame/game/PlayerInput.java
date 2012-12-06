package org.seattlegamer.spacegame.game;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.KeyThrottle;
import org.seattlegamer.spacegame.StateSwitch;

public class PlayerInput extends Component {

	public PlayerInput(Bus bus, UUID entityId) {
		super(bus, entityId);
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		if(KeyThrottle.canExecute(input, KeyEvent.VK_ESCAPE, elapsedMillis)) {
			this.bus.broadcast(StateSwitch.MAIN_MENU_WITH_RESUME);
		}

	}

	@Override
	public void registerHandlers() {}

	@Override
	public void deregisterHandlers() {}
	
}
