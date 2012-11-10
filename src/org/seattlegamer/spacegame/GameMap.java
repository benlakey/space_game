package org.seattlegamer.spacegame;

import java.util.Collection;
import java.util.LinkedList;

import org.seattlegamer.spacegame.sprites.FilesystemBasedSpriteCache;
import org.seattlegamer.spacegame.sprites.Sprite;
import org.seattlegamer.spacegame.sprites.SpriteCache;

public class GameMap {

	private final String name;
	private final int requiredPlayerCount;
	
	private GameMap(String name, int requiredPlayerCount) {
		this.name = name;
		this.requiredPlayerCount = requiredPlayerCount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRequiredPlayerCount() {
		return requiredPlayerCount;
	}
	
	public Iterable<Sprite> getSprites() {
		
		//TODO: load these from a resource (text file? serialized data?)
		SpriteCache cache = FilesystemBasedSpriteCache.get();
		Sprite sprite = cache.getSprite(PlanetType.MARS.getAssetPath());
		Collection<Sprite> sprites = new LinkedList<Sprite>();
		sprites.add(sprite);
		return sprites;
		
	}
	
	public static GameMap load(String mapResource) {
		//TODO: loaded from the file/serialized map, not hardcoded.
		return new GameMap("Test Map", 1);
	}

}
