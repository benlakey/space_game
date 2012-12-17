package org.seattlegamer.spacegame.messages;

public class MenuSelectionChanged {

	private final int selectedIndex;
	
	public MenuSelectionChanged(int index) {
		this.selectedIndex = index;
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}

}
