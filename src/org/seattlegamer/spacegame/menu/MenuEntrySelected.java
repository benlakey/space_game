package org.seattlegamer.spacegame.menu;

import org.seattlegamer.spacegame.Message;

public class MenuEntrySelected implements Message {

	private final int index;
	
	public MenuEntrySelected(int index) {
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
