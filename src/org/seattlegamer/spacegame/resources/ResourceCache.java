package org.seattlegamer.spacegame.resources;

import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class ResourceCache {
	
	private static ResourceCache instance = new ResourceCache();
	private static final Object instanceLock = new Object();
	private static Logger logger = Logger.getLogger(ResourceCache.class);
	
	private static ResourceCache getResourceCache() {
		synchronized(instanceLock) {
			if(instance == null) {
				instance = new ResourceCache();
			}
			return instance;
		}
	}
	
	public static Image getImage(String name) {
		ResourceCache resourceCache = getResourceCache();
		ResourceLoader<Image> loader = new ImageResourceLoader();
		try {
			return resourceCache.get(name, loader);
		} catch (IOException e) {
			throw new RuntimeException("Resource missing! Resource: " + name, e);
		}
	}
	
	private ConcurrentHashMap<String, Object> cache;

	private ResourceCache() {
		this.cache = new ConcurrentHashMap<String, Object>();
	}

	public <T> T get(String name, ResourceLoader<T> loader) throws IOException {
		
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
		
		Class<T> loadableType = loader.getLoadableType();
		return loadableType.cast(retrieved);

	}

}
