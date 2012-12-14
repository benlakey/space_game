package org.seattlegamer.spacegame.ui;

import java.util.UUID;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;
import org.seattlegamer.spacegame.Subscription;

public class MenuEntry extends Component {
	
	private final int index;
	private final MenuAction action;

	public MenuEntry(ComponentBus bus, UUID entityId, int index, MenuAction action) {
		super(bus, entityId);
		this.index = index;
		this.action = action;
	}
	
	@Subscription
	public void execute(MenuExecution execution) {
		if(this.index == execution.getIndex()) {
			this.action.execute(this.bus);
		}
	}

}
