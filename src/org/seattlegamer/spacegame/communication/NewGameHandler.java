package org.seattlegamer.spacegame.communication;

import java.util.LinkedList;
import java.util.List;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.activities.ComponentBase;
import org.seattlegamer.spacegame.activities.GameComponent;

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

		List<Player> players = new LinkedList<Player>();

		players.add(new Player(newGame.getPlayer1Name()));
		players.add(new Player(newGame.getPlayer2Name()));
		
		GameMap testMap = GameMap.load(newGame.getMapResource());
		ComponentBase gameComponent = new GameComponent(this.bus, players, testMap);
		ComponentTransition transition = new ComponentTransition(gameComponent);
		
		this.bus.send(transition);
		
	}

}
