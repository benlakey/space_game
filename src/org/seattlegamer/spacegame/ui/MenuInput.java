package org.seattlegamer.spacegame.ui;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.GameState;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.KeyThrottle;
import org.seattlegamer.spacegame.StateManager;
import org.seattlegamer.spacegame.utils.NumberUtils;

public class MenuInput extends Component {

	private final int maxEntries;
	private int selectionIndex;

	public MenuInput(ComponentBus bus, UUID entityId, int maxEntries) {
		super(bus, entityId);
		this.maxEntries = maxEntries;
	}
	
	@Override
	public void update(Input input, long elapsedMillis) {
		
		if(KeyThrottle.executable(input, KeyEvent.VK_UP, elapsedMillis)) {
			this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex - 1, maxEntries - 1);
			this.bus.broadcast(new MenuSelectionChanged(this.selectionIndex));
		} else if(KeyThrottle.executable(input, KeyEvent.VK_DOWN, elapsedMillis)) {
			this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex + 1, maxEntries - 1);
			this.bus.broadcast(new MenuSelectionChanged(this.selectionIndex));
		} else if(KeyThrottle.executable(input, KeyEvent.VK_ESCAPE, elapsedMillis)) {
			StateManager.setState(GameState.getCurrent());
		} else if(KeyThrottle.executable(input, KeyEvent.VK_ENTER, elapsedMillis)) {
			this.bus.broadcast(new MenuExecution(this.selectionIndex));
		}
		
	}

}
