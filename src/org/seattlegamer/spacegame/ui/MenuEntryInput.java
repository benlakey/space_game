package org.seattlegamer.spacegame.ui;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.utils.NumberUtils;
import org.seattlegamer.spacegame.utils.Throttle;

public class MenuEntryInput extends Component {

	private static final long KEY_DELAY = 300;
	
	private int selectionIndex;
	private final int maxIndex;
	private final Throttle downThrottle;
	private final Throttle upThrottle;

	public MenuEntryInput(Bus bus, UUID entityId, int selectionIndex, int maxIndex) {
		super(bus, entityId);
		this.selectionIndex = selectionIndex;
		this.maxIndex = maxIndex;
		this.downThrottle = new Throttle(KEY_DELAY);
		this.upThrottle = new Throttle(KEY_DELAY);
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		checkThrottledInput(input, KeyEvent.VK_UP, elapsedMillis, this.upThrottle, -1);
		checkThrottledInput(input, KeyEvent.VK_DOWN, elapsedMillis, this.downThrottle, 1);

		if(input.isKeyInputActive(KeyEvent.VK_ENTER)) {
			this.bus.send(new MenuEntryExecution(this.selectionIndex), this.entityId);
		}

	}
	
	//TODO: this is duplicated code elsewhere. centralize
	private void checkThrottledInput(Input input, int inputCode, long elapsedMillis, Throttle throttle, int change) {
		if(input.isKeyInputActive(inputCode)) {
			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();
			if(remaining == 0) {
				this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex + change, this.maxIndex);
				this.bus.broadcast(new MenuEntryChange(this.selectionIndex));
				throttle.throttle();
			}
		} else {
			throttle.unthrottle();
		}
	}

}
