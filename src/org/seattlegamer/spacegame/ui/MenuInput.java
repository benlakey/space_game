package org.seattlegamer.spacegame.ui;

import java.awt.event.KeyEvent;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentGroup;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.utils.NumberUtils;
import org.seattlegamer.spacegame.utils.Throttle;

public class MenuInput extends Component {

	private static final long KEY_DELAY = 300;
	
	private int selectionIndex;
	private final int size;
	private final Throttle downThrottle;
	private final Throttle upThrottle;

	public MenuInput(Entity owner, int selectionIndex, int size) {
		super(owner);
		this.selectionIndex = selectionIndex;
		this.size = size;
		this.setGroupMembership(ComponentGroup.MENU, true);
		this.downThrottle = new Throttle(KEY_DELAY);
		this.upThrottle = new Throttle(KEY_DELAY);
	}

	@Override
	public void update(Input input, long elapsedMillis) {

		checkThrottledInput(input, KeyEvent.VK_UP, elapsedMillis, this.upThrottle, -1);
		checkThrottledInput(input, KeyEvent.VK_DOWN, elapsedMillis, this.downThrottle, 1);
		
		if(input.isKeyInputActive(KeyEvent.VK_ENTER)) {
			this.entity.broadcast(MenuEntryExecution.class, new MenuEntryExecution(this.selectionIndex));
		}

	}
	
	//TODO: this is duplicated code elsewhere. centralize
	private void checkThrottledInput(Input input, int inputCode, long elapsedMillis, Throttle throttle, int change) {
		if(input.isKeyInputActive(inputCode)) {
			throttle.tick(elapsedMillis);
			long remaining = throttle.timeRemaining();
			if(remaining == 0) {
				this.selectionIndex = NumberUtils.wrap(this.selectionIndex + change, this.size - 1);
				this.entity.broadcast(MenuEntryChange.class, new MenuEntryChange(this.selectionIndex));
				throttle.throttle();
			}
		} else {
			throttle.unthrottle();
		}
	}

}
