package org.seattlegamer.spacegame;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.ui.MenuState;

public class PlayerInput extends Component {
	
	public PlayerInput(ComponentBus bus, UUID entityId) {
		super(bus, entityId);
	}

	@Override
	public void update(Input input, long elapsedMillis) {
		
		if(KeyThrottle.executable(input, KeyEvent.VK_ESCAPE, elapsedMillis)) {
			StateManager.setState(new MenuState());
		}
		
	}
	
}
