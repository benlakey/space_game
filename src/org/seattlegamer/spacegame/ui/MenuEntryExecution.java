package org.seattlegamer.spacegame.ui;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class MenuEntryExecution extends Message {
	
	private final int selectionIndex;
	
	public MenuEntryExecution(UUID sourceEntityId, int selectionIndex) {
		super(sourceEntityId);
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
