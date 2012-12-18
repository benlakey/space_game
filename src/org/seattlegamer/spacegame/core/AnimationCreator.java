package org.seattlegamer.spacegame.core;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.messages.AnimationStart;
import org.seattlegamer.spacegame.resources.ResourceCache;
import org.seattlegamer.spacegame.utils.GraphicsUtils;

public class AnimationCreator {
	
	private final Bus<Component> bus;
	private final ResourceCache resourceCache;
	private final GameSettings settings;

	public AnimationCreator(Bus<Component> bus, ResourceCache resourceCache, GameSettings settings) {
		this.bus = bus;
		this.resourceCache = resourceCache;
		this.settings = settings;
	}

	public Iterable<Component> createAnimation(String asset, int x, int y) throws IOException {

		Collection<Component> components = new LinkedList<Component>();
		
		final UUID explosionEntityId = UUID.randomUUID();
		
		Image explosionImage = resourceCache.getImage(asset);
		Image scaledExplosionImage = GraphicsUtils.getScaledImage(explosionImage, settings.getScale());

		AnimationLoader animationLoader = new AnimationLoader(scaledExplosionImage);
		Image[] frames = animationLoader.getFrames();

		components.add(new Animation(bus, explosionEntityId, frames, new Point(x, y)));

		bus.send(new AnimationStart(), explosionEntityId);
		
		return components;

	}
	
}
