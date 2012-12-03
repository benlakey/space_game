package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Message;

public class MenuEntryChange implements Message {

	private final int index;

	public MenuEntryChange(int index) {
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
