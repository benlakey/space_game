package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentGroup;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;

public class MenuEntry extends Component {
	
	private final int index;
	private final Message executionMessage;
	private boolean selected;

	public MenuEntry(Entity entity, int index, Message message) {
		super(entity);
		this.index = index;
		this.executionMessage = message;
		this.setGroupMembership(ComponentGroup.MENU, true);
		this.entity.register(MenuEntryChange.class, this.getMenuEntryChangeHandler());
		this.entity.register(MenuEntryExecution.class, this.getMenuEntryExecutionHandler());
	}

	private Handler<MenuEntryChange> getMenuEntryChangeHandler() {
		return new Handler<MenuEntryChange>() {
			@Override
			public void handle(MenuEntryChange message) {
				selected = index == message.getIndex();
			}
		};
	}
	
	private Handler<MenuEntryExecution> getMenuEntryExecutionHandler() {
		return new Handler<MenuEntryExecution>() {
			@Override
			public void handle(MenuEntryExecution message) {
				if(selected) {
					entity.broadcast(executionMessage);
				}
			}
		};
	}

}
