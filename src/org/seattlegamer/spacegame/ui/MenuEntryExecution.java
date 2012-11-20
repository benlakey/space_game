package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Message;

public class MenuEntryExecution implements Message {
	
	private final int selectionIndex;
	
	public MenuEntryExecution(int selectionIndex) {
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
