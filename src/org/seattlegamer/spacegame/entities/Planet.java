package org.seattlegamer.spacegame.entities;

import org.seattlegamer.spacegame.sprites.Sprite;

public class Planet {

	private final Sprite sprite;

	public Planet(Sprite sprite) {
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return this.sprite;
	}

}