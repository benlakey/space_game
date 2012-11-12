package org.seattlegamer.spacegame;

import org.seattlegamer.spacegame.sprites.Sprite;

public class SpaceBody {

	private final Sprite sprite;

	public SpaceBody(Sprite sprite) {
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return this.sprite;
	}

}