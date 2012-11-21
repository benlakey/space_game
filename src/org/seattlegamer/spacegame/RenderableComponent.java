package org.seattlegamer.spacegame;

import java.awt.Graphics2D;

public abstract class RenderableComponent extends Component {
	
	protected RenderableComponent(Handler owner) {
		super(owner);
	}
	
	public abstract void render(Graphics2D graphics);

}
