package org.seattlegamer.spacegame;

import java.awt.Graphics2D;

public interface GameCanvas {

	Graphics2D getGraphics();
	int getWidth();
	int getHeight();
	void showNextBuffer();
	
}
