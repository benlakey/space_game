package org.seattlegamer.spacegame.menu;

import org.seattlegamer.spacegame.Message;

public class MenuEntryExecuted implements Message {
	
	private final int selectionIndex;
	
	public MenuEntryExecuted(int selectionIndex) {
		this.selectionIndex = selectionIndex;
	}
	
	public int getSelectionIndex() {
		return selectionIndex;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
