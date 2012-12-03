package org.seattlegamer.spacegame.resources;

import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class InMemoryResourceCache implements ResourceCache {

	private static final Logger logger = Logger.getLogger(InMemoryResourceCache.class);

	private ResourceLoader<Image> imageResourceLoader;
	private ConcurrentHashMap<String, Object> cache;

	public InMemoryResourceCache(ResourceLoader<Image> imageResourceLoader) {
		this.imageResourceLoader = imageResourceLoader;
		this.cache = new ConcurrentHashMap<String, Object>();
	}

	@Override
	public Image getImage(String name) throws IOException {
		return this.get(name, this.imageResourceLoader);
	}

	private <T> T get(String name, ResourceLoader<T> loader) throws IOException {
		
		Object retrieved = this.cache.get(name);
		
		if(retrieved == null) {
			
			logger.info("Cache miss. Loading " + name);
			
			Object loaded = loader.load(name);
			if(loaded == null) {
				return null;
			}
			Object existing = this.cache.putIfAbsent(name, loaded);
			if(existing == null) {
				retrieved = loaded;
			}
			
			logger.info(name + " is cached.");

		}

		return loader.getResourceType().cast(retrieved);

	}

}
