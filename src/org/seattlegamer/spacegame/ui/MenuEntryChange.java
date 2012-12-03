package org.seattlegamer.spacegame.ui;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class MenuEntryChange extends Message {

	private final int index;

	public MenuEntryChange(UUID sourceEntityId, int index) {
		super(sourceEntityId);
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}

	@Override
	public String toString() {
		return String.format("%s - index %d selected", this.getClass().getSimpleName(), this.index);
	}

}
