package org.seattlegamer.spacegame.ui;

import java.util.UUID;

import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.Subscription;
import org.seattlegamer.spacegame.messages.MenuExecution;

public class MenuEntry extends Component {
	
	private final int index;
	private final MenuAction action;

	public MenuEntry(Bus<Component> bus, UUID entityId, int index, MenuAction action) {
		super(bus, entityId);
		this.index = index;
		this.action = action;
	}
	
	@Subscription
	public void execute(MenuExecution execution) {
		if(this.index == execution.getIndex()) {
			this.action.execute();
		}
	}

}
