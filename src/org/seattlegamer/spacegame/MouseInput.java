package org.seattlegamer.spacegame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

	private MouseListener innerMouseListener;
	private MouseMotionListener innerMouseMotionListener;
	
	public void setMouseListener(MouseListener mouseListener) {
		this.innerMouseListener = mouseListener;
	}
	
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.innerMouseMotionListener = mouseMotionListener;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.innerMouseMotionListener.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.innerMouseMotionListener.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.innerMouseListener.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.innerMouseListener.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.innerMouseListener.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.innerMouseListener.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.innerMouseListener.mouseExited(e);
	}

	
}
