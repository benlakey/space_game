package org.seattlegamer.spacegame.communication;

import java.util.LinkedList;
import java.util.List;

import org.seattlegamer.spacegame.GameMap;
import org.seattlegamer.spacegame.Player;
import org.seattlegamer.spacegame.activities.GameActivity;

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
		//TODO: gather input from players at start thru menu systems (put in NewGame command)
		players.add(new Player("John Doe"));
		players.add(new Player("Bob Smith"));
		
		GameMap testMap = GameMap.load(newGame.getMapResource());
		GameActivity gameActivity = new GameActivity(this.bus, players, testMap);
		ActivityTransition transition = new ActivityTransition(gameActivity);
		
		this.bus.send(transition);
		
	}

}
