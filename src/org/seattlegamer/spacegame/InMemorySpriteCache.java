package org.seattlegamer.spacegame;


public class InMemorySpriteCache extends SpriteCache {

	private static InMemorySpriteCache instance = new InMemorySpriteCache();
	
	public static InMemorySpriteCache get() {
		return instance;
	}

	private InMemorySpriteCache() {}
	
	@Override
	protected Sprite handleCacheMiss(String key) {
		return null;
	}

}
