package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentGroup;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;

public class MenuEntry extends Component {
	
	private final int index;
	private boolean selected;
	private Message message;
	
	public MenuEntry(Handler owner, int index, Message message) {
		this(owner, index, message, false);
	}
	
	public MenuEntry(Handler owner, int index, Message message, boolean selected) {
		super(owner);
		this.index = index;
		this.message = message;
		this.selected = selected;
		this.applyGroup(ComponentGroup.MENU);
	}
	
	@Override
	public void handle(Message message) {
		
		if(message instanceof MenuEntryChange) {
			MenuEntryChange change = (MenuEntryChange)message;
			this.handleMenuEntryChange(change);
		} else if(message instanceof MenuEntryExecution) {
			this.handleMenuEntryExecution();
		}

	}

	private void handleMenuEntryChange(MenuEntryChange change) {
		this.selected = this.index == change.getIndex();
	}
	
	private void handleMenuEntryExecution() {
		if(this.selected) {
			this.owner.handle(this.message);
		}
	}

}
