package org.seattlegamer.spacegame;

import java.awt.Graphics2D;

import org.seattlegamer.spacegame.communication.Handler;

public interface GameCanvas extends Handler {
	int getWidth();
	int getHeight();
	Graphics2D getGraphics();
	void showNextBuffer();
}
