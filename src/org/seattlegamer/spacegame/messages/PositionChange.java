package org.seattlegamer.spacegame.messages;


public class PositionChange {

	private final int positionDiffX;
	private final int positionDiffY;
	
	public PositionChange(int positionDiffX, int positionDiffY) {
		this.positionDiffX = positionDiffX;
		this.positionDiffY = positionDiffY;
	}

	public int getPositionDiffX() {
		return this.positionDiffX;
	}

	public int getPositionDiffY() {
		return this.positionDiffY;
	}

}
