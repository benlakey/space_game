package org.seattlegamer.spacegame.ui;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.KeyThrottle;
import org.seattlegamer.spacegame.StateSwitch;
import org.seattlegamer.spacegame.utils.NumberUtils;

public class MenuEntryInput extends Component {

	private int selectionIndex;
	private final int maxIndex;

	public MenuEntryInput(Bus bus, UUID entityId, int selectionIndex, int maxIndex) {
		super(bus, entityId);
		this.selectionIndex = selectionIndex;
		this.maxIndex = maxIndex;
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		if(KeyThrottle.canExecute(input, KeyEvent.VK_UP, elapsedMillis)) {
			this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex - 1, this.maxIndex);
			this.bus.broadcast(new MenuEntryChange(this.selectionIndex));
		}
		
		if(KeyThrottle.canExecute(input, KeyEvent.VK_DOWN, elapsedMillis)) {
			this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex + 1, this.maxIndex);
			this.bus.broadcast(new MenuEntryChange(this.selectionIndex));
		}
		
		if(KeyThrottle.canExecute(input, KeyEvent.VK_ESCAPE, elapsedMillis)) {
			this.bus.broadcast(StateSwitch.GAME);
		}

		if(input.isKeyInputActive(KeyEvent.VK_ENTER)) {
			this.bus.broadcast(new MenuEntryExecution(this.selectionIndex));
		}

	}

}
