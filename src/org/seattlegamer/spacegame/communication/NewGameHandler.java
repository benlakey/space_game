package org.seattlegamer.spacegame.communication;

import java.util.LinkedList;
import java.util.List;

import org.seattlegamer.spacegame.activities.GameActivity;
import org.seattlegamer.spacegame.entities.GameMap;
import org.seattlegamer.spacegame.entities.Player;

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
		players.add(new Player());
		
		GameMap testMap = GameMap.load(newGame.getMapResource());
		GameActivity gameActivity = new GameActivity(this.bus, players, testMap);
		ActivityTransition transition = new ActivityTransition(gameActivity);
		
		this.bus.send(transition);
		
	}

}
