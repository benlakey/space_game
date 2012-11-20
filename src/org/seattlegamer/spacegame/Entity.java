package org.seattlegamer.spacegame;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public final class Entity implements Handler {

	private static Logger logger = Logger.getLogger(Entity.class);

	private final Collection<Component> components;
	protected final Collection<RenderableComponent> renderables;
	private final Collection<Handler> handlers;

	public Entity() {
		this.components = new LinkedList<Component>();
		this.renderables = new LinkedList<RenderableComponent>();
		this.handlers = new LinkedList<Handler>();
	}

	public void add(Component component) {
		this.components.add(component);
		if(component instanceof RenderableComponent) {
			this.renderables.add((RenderableComponent)component);
		}
		this.handlers.add(component);
	}
	
	public void registerHandler(Handler handler) {
		this.handlers.add(handler);
	}
	
	@Override
	public void handle(Message message) {
		logger.info("broadcasting " + message);
		for(Handler handler : this.handlers) {
			handler.handle(message);
		}
	}
	
	public void setEnabled(boolean enabled) {
		for(Component component : this.components) {
			component.setEnabled(enabled);
		}
	}
	
	public void update(Input input, long elapsedMillis) {
		for(Component component : this.components) {
			if(component.isEnabled()) {
				component.update(input, elapsedMillis);
			}
		}
	}
	
	public void render(Graphics2D graphics) {
		for(RenderableComponent renderable : this.renderables) {
			if(renderable.isEnabled()) {
				renderable.render(graphics);
			}
		}
	}

}
