package org.seattlegamer.spacegame.ui;

import org.seattlegamer.spacegame.Message;

public class MenuEntrySelection implements Message {

	private final int index;
	
	public MenuEntrySelection(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":" + this.index;
	}
	
}
