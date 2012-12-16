package org.seattlegamer.spacegame.ui;

import java.awt.event.KeyEvent;
import java.util.UUID;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.GameState;
import org.seattlegamer.spacegame.Input;
import org.seattlegamer.spacegame.StateManager;
import org.seattlegamer.spacegame.utils.NumberUtils;
import org.seattlegamer.spacegame.utils.Throttle;

public class MenuInput extends Component {

	private final int KEY_DELAY_MILLIS = 300;
	
	private int selectionIndex;
	private final StateManager stateManager;
	private final int maxEntries;
	private final Throttle upThrottle;
	private final Throttle downThrottle;
	private final Throttle executeThrottle;

	public MenuInput(ComponentBus bus, UUID entityId, StateManager stateManager, int maxEntries) {
		super(bus, entityId);
		this.stateManager = stateManager;
		this.maxEntries = maxEntries;
		this.upThrottle = new Throttle(KEY_DELAY_MILLIS);
		this.downThrottle = new Throttle(KEY_DELAY_MILLIS);
		this.executeThrottle = new Throttle(KEY_DELAY_MILLIS);
	}
	
	@Override
	public void update(Input input, long elapsedMillis) {
		
		this.tickThrottles(elapsedMillis);
		
		if(input.isKeyInputActive(KeyEvent.VK_UP)) {
			if(this.upThrottle.getMillisUntilExecution() == 0) {
				this.scroll(-1);
			}
		} else {
			this.upThrottle.unthrottle();
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_DOWN)) {
			if(this.downThrottle.getMillisUntilExecution() == 0) {
				this.scroll(1);
			}
		} else {
			this.downThrottle.unthrottle();
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_ENTER)) {
			if(this.executeThrottle.getMillisUntilExecution() == 0) {
				this.bus.broadcast(new MenuExecution(this.selectionIndex));
			}
		} else {
			this.executeThrottle.unthrottle();
		}
		
		if(input.isKeyInputActive(KeyEvent.VK_ESCAPE)) {
			if(MenuState.menuToggleThrottle.getMillisUntilExecution() == 0) {
				this.stateManager.changeState(GameState.getCurrentGame());
			}
		} else {
			MenuState.menuToggleThrottle.unthrottle();
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
