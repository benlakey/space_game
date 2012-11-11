package org.seattlegamer.spacegame.communication;


public class NewGame implements Command {

	private final String mapResource;
	private String player1Name;
	private String player2Name;
	
	public NewGame(String mapResource) {
		this.mapResource = mapResource;
	}

	public String getMapResource() {
		return this.mapResource;
	}

	public String getPlayer1Name() {
		return this.player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return this.player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

}
