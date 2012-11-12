package org.seattlegamer.spacegame;

public enum PlanetType {

	MARS("assets/mars.png");
	
	private String assetPath;
	
	private PlanetType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return this.assetPath;
	}
	
}
