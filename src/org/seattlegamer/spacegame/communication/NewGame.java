package org.seattlegamer.spacegame.communication;

public class NewGame implements Command {

	private final String mapResource;
	
	public NewGame(String mapResource) {
		this.mapResource = mapResource;
	}

	public String getMapResource() {
		return mapResource;
	}

}
