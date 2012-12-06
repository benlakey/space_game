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
	
	private final Handler<MenuEntryChange> menuEntryChangeHandler = this.getMenuEntryChangeHandler();
	private final Handler<MenuEntryExecution> menuEntryExecution = this.getMenuEntryExecutionHandler();

	public MenuEntry(Bus bus, UUID entityId, int index, Message message) {
		super(bus, entityId);
		this.index = index;
		this.executionMessage = message;
		
	}
	
	@Override
	public void registerHandlers() {
		this.bus.register(MenuEntryChange.class, menuEntryChangeHandler);
		this.bus.register(MenuEntryExecution.class, menuEntryExecution);
	}
	
	@Override
	public void deregisterHandlers() {
		this.bus.deregister(MenuEntryChange.class, menuEntryChangeHandler);
		this.bus.deregister(MenuEntryExecution.class, menuEntryExecution);
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
