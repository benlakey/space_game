package org.seattlegamer.spacegame.core;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.resources.ResourceCache;

public class AnimationCreator {
	
	private final Bus bus;
	private final ResourceCache resourceCache;

	public AnimationCreator(Bus bus, ResourceCache resourceCache) {
		this.bus = bus;
		this.resourceCache = resourceCache;
	}

	public Iterable<Component> createAnimation(String asset, int x, int y) throws IOException {

		Collection<Component> components = new LinkedList<Component>();
		
		final UUID explosionEntityId = UUID.randomUUID();
		
		Image explosionImage = resourceCache.getImage(asset);
		AnimationLoader animationLoader = new AnimationLoader(explosionImage);
		Image[] frames = animationLoader.getFrames();

		components.add(new Animation(bus, explosionEntityId, frames, new Point(x, y)));
		bus.send(new AnimationStart(), explosionEntityId);
		
		return components;

	}
	
}
