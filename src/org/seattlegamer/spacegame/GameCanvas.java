package org.seattlegamer.spacegame;

import java.awt.Graphics2D;

public interface GameCanvas {
	int getWidth();
	int getHeight();
	Graphics2D getGraphics();
	void showNextBuffer();
}
