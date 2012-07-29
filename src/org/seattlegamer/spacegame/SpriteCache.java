package org.seattlegamer.spacegame;

import java.util.HashMap;

import org.apache.log4j.Logger;

public abstract class SpriteCache {

	private static Logger logger = Logger.getLogger(SpriteCache.class);
	private static final Object cacheLock = new Object();
	
	protected HashMap<String, Sprite> sprites;
	
	protected SpriteCache() {
		this.sprites = new HashMap<String, Sprite>();
	}
	
	public Sprite getSprite(String key) {
		
		synchronized (cacheLock) {
			
			if(this.sprites.containsKey(key)) {
				logger.info(String.format("Cache hit for key '%s'", key));
				return (Sprite)this.sprites.get(key);
			}
			
			return this.handleCacheMiss(key);
		}
		
	}

	protected abstract Sprite handleCacheMiss(String key);
	
	public void clear() {
		synchronized (cacheLock) {
			this.sprites.clear();
		}
	}
	
	public void cacheSprite(String key, Sprite sprite) {
		synchronized (cacheLock) {
			this.sprites.put(key, sprite);
		}
	}

}
