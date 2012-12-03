package org.seattlegamer.spacegame.ui;

import java.util.UUID;

import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;

public class MenuEntry extends Component {
	
	private final int index;
	private final Message executionMessage;
	private boolean selected;

	public MenuEntry(Bus bus, UUID entityId, int index, Message message) {
		super(bus, entityId);
		this.index = index;
		this.executionMessage = message;
		this.bus.register(MenuEntryChange.class, this.getMenuEntryChangeHandler());
		this.bus.register(MenuEntryExecution.class, this.getMenuEntryExecutionHandler());
	}

	private Handler<MenuEntryChange> getMenuEntryChangeHandler() {
		return new Handler<MenuEntryChange>() {

			@Override
			public void handle(MenuEntryChange message) {
				selected = index == message.getIndex();
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
			}

		};
	}
	
	private Handler<MenuEntryExecution> getMenuEntryExecutionHandler() {
		return new Handler<MenuEntryExecution>() {

			@Override
			public void handle(MenuEntryExecution message) {
				if(selected) {
					bus.broadcast(executionMessage);
				}
			}

			@Override
			public UUID getEntityIdHandlingFor() {
				return entityId;
			}

		};
	}

}
