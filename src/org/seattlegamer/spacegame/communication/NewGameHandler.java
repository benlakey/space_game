package org.seattlegamer.spacegame.communication;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.components.ComponentBase;
import org.seattlegamer.spacegame.components.GameComponent;
import org.seattlegamer.spacegame.components.HeadsUpDisplay;

public class NewGameHandler implements Handler {

	private final Bus bus;

	public NewGameHandler(Bus bus) {
		this.bus = bus;
	}
	
	@Override
	public <T extends Command> boolean canHandle(T command) {
		return command instanceof NewGame;
	}

	@Override
	public void handle(Command command) {

		NewGame newGame = (NewGame)command;

		GameMap testMap = GameMap.load(newGame.getMapResource());
		
		Player player1 = new Player(newGame.getPlayer1Name(), testMap.getPlayer1SpaceBody());
		Player player2 = new Player(newGame.getPlayer2Name(), testMap.getPlayer2SpaceBody());
		
		HeadsUpDisplay headsUpDisplay = new HeadsUpDisplay();
		
		this.bus.register(headsUpDisplay);
		
		ComponentBase gameComponent = new GameComponent(this.bus, player1, player2, testMap, headsUpDisplay);
		ComponentTransition transition = new ComponentTransition(gameComponent);
		
		this.bus.send(transition);
		
	}

}
