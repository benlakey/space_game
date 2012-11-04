package org.seattlegamer.spacegame;

import java.util.HashMap;
import java.util.Map;

public abstract class SpriteCache {

	private static final Object cacheLock = new Object();
	
	protected Map<String, Sprite> sprites;
	
	protected SpriteCache() {
		this.sprites = new HashMap<String, Sprite>();
	}
	
	public Sprite getSprite(String key) {
		
		synchronized (cacheLock) {
			
			if(this.sprites.containsKey(key)) {
				return this.sprites.get(key);
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
