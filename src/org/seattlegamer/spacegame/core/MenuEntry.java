package org.seattlegamer.spacegame.core;

import java.util.UUID;

import org.seattlegamer.spacegame.messages.MenuExecution;

public class MenuEntry extends Component {
	
	private final int index;
	private final MenuAction action;

	public MenuEntry(Bus bus, UUID entityId, int index, MenuAction action) {
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
