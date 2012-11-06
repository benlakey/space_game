package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.seattlegamer.spacegame.sprites.InMemorySpriteCache;
import org.seattlegamer.spacegame.sprites.Sprite;
import org.seattlegamer.spacegame.sprites.SpriteCache;

public class SpriteCacheTests {
	
	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}

	@Test
	public void spriteCacheCanRetrievePreviouslyCachedSprite() throws IOException {
		
		final String cacheKey = "foo";
		
		SpriteCache cache = InMemorySpriteCache.get();
		cache.clear();
		
		Sprite sprite = new Sprite(null);
		cache.cacheSprite(cacheKey, sprite);
		
		Sprite retrieved = cache.getSprite(cacheKey);
		assertNotNull(retrieved);
		
	}
	
	@Test
	public void spriteCacheReturnsNullForUnknownSprite() throws IOException {
		
		final String cacheKey = "foo";
		
		SpriteCache cache = InMemorySpriteCache.get();
		cache.clear();
		
		Sprite retrieved = cache.getSprite(cacheKey);
		assertNull(retrieved);
		
	}
	
}
