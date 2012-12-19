package org.seattlegamer.spacegame.core;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.MenuExecution;
import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.messages.StateChange;
import org.seattlegamer.spacegame.utils.NumberUtils;
import org.seattlegamer.spacegame.utils.Throttle;

public class MenuInput extends Component {

	private final int KEY_DELAY_MILLIS = 300;
	
	private int selectionIndex;
	private final int maxEntries;
	private final Throttle upThrottle;
	private final Throttle downThrottle;
	private final Throttle executeThrottle;

	public MenuInput(Bus bus, UUID entityId, int maxEntries) {
		super(bus, entityId);
		this.maxEntries = maxEntries;
		this.upThrottle = new Throttle(KEY_DELAY_MILLIS);
		this.downThrottle = new Throttle(KEY_DELAY_MILLIS);
		this.executeThrottle = new Throttle(KEY_DELAY_MILLIS);
	}
	
	@Override
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {
		
		this.tickThrottles(elapsedMillis);
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_UP)) {
			if(this.upThrottle.getMillisUntilExecution() == 0) {
				this.scroll(-1);
			}
		} else {
			this.upThrottle.unthrottle();
		}
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_DOWN)) {
			if(this.downThrottle.getMillisUntilExecution() == 0) {
				this.scroll(1);
			}
		} else {
			this.downThrottle.unthrottle();
		}
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_ENTER)) {
			long millisUntilExecution = this.executeThrottle.getMillisUntilExecution();
			if(millisUntilExecution == 0) {
				this.bus.broadcast(new MenuExecution(this.selectionIndex));
			}
		} else {
			this.executeThrottle.unthrottle();
		}

		if(keyInput.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			this.bus.broadcast(new StateChange(GameState.class));
		}

	}
	
	private void tickThrottles(long elapsedMillis) {
		this.upThrottle.tick(elapsedMillis);
		this.downThrottle.tick(elapsedMillis);
		this.executeThrottle.tick(elapsedMillis);
	}
	
	private void scroll(int scrollChange) {
		this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex + scrollChange, this.maxEntries - 1);
		this.bus.broadcast(new MenuSelectionChanged(this.selectionIndex));
	}

}
