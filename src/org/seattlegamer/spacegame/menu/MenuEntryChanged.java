package org.seattlegamer.spacegame.menu;

import java.util.UUID;

import org.seattlegamer.spacegame.Message;

public class MenuEntryChanged implements Message {

	private final UUID componentId;
	private final String text;
	private final boolean selected;
	private final int index;
	
	public MenuEntryChanged(UUID componentId, String text, boolean selected, int index) {
		this.componentId = componentId;
		this.text = text;
		this.selected = selected;
		this.index = index;
	}
	
	public UUID getComponentId() {
		return this.componentId;
	}
	
	public String getText() {
		return this.text;
	}

	public boolean isSelected() {
		return this.selected;
	}
	
	public int getIndex() {
		return this.index;
	}

	@Override
	public String toString() {
		return String.format("%s - text: %s, selected: %s", this.getClass().getSimpleName(), this.text, this.selected);
	}

}
