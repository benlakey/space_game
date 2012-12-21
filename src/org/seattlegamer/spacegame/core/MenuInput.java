package org.seattlegamer.spacegame.core;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.MenuExecution;
import org.seattlegamer.spacegame.messages.MenuSelectionChanged;
import org.seattlegamer.spacegame.messages.StateChange;
import org.seattlegamer.spacegame.utils.NumberUtils;

public class MenuInput extends Component {

	private static final int KEY_DELAY_MILLIS = 300;
	
	private int selectionIndex;
	private final int maxEntries;
	private boolean scrollReady;
	private final Timer scrollThrottle;

	public MenuInput(Bus bus, UUID entityId, int maxEntries) {
		super(bus, entityId);
		this.maxEntries = maxEntries;
		this.scrollReady = true;
		this.scrollThrottle = new Timer();
	}
	
	@Override
	public void update(KeyInput keyInput, PointerInput pointerInput, long elapsedMillis) {

		if(keyInput.isKeyInputActive(KeyEvent.VK_UP) && this.scrollReady) {
			this.scroll(-1);
			this.throttleScrolling();
		} else if(keyInput.isKeyInputActive(KeyEvent.VK_DOWN) && this.scrollReady) {
			this.scroll(1);
			this.throttleScrolling();
		}
		
		if(keyInput.isKeyInputActive(KeyEvent.VK_ENTER)) {
			this.bus.broadcast(new MenuExecution(this.selectionIndex));
		}

		if(keyInput.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			this.bus.broadcast(new StateChange(GameState.class));
		}

	}
	
	private void throttleScrolling() {
		this.scrollReady = false;
		this.scrollThrottle.schedule(new TimerTask() {
			@Override
			public void run() {
				scrollReady = true;
			}
		}, KEY_DELAY_MILLIS);
	}

	private void scroll(int scrollChange) {
		this.selectionIndex = NumberUtils.wrapIndex(this.selectionIndex + scrollChange, this.maxEntries - 1);
		this.bus.broadcast(new MenuSelectionChanged(this.selectionIndex));
	}

}
