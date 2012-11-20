package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentGroup;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Message;

public class MenuEntry extends Component {
	
	private final int index;
	private final String text;
	private boolean selected;
	private Message message;
	
	public MenuEntry(Handler owner, int index, String text, Message message) {
		this(owner, index, text, message, false);
	}
	
	public MenuEntry(Handler owner, int index, String text, Message message, boolean selected) {
		super(owner);
		this.index = index;
		this.text = text;
		this.message = message;
		this.selected = selected;
		this.applyGroup(ComponentGroup.MENU);
	}
	
	public void advertiseSelf() {
		this.owner.handle(new MenuEntryChange(this.id, this.text, this.selected, this.index));
	}
	
	@Override
	public void handle(Message message) {
		
		if(message instanceof MenuEntrySelection) {
			MenuEntrySelection menuEntrySelection = (MenuEntrySelection)message;
			this.handleMenuEntrySelection(menuEntrySelection);
		} else if(message instanceof MenuEntryExecution) {
			this.handleMenuEntryExecution();
		}

	}

	private void handleMenuEntrySelection(MenuEntrySelection menuEntrySelection) {
		if(this.index == menuEntrySelection.getIndex()) {
			this.selected = true;
		} else {
			this.selected = false;
		}
		
		this.advertiseSelf();
	}
	
	private void handleMenuEntryExecution() {
		if(this.selected) {
			this.owner.handle(this.message);
		}
	}

}
