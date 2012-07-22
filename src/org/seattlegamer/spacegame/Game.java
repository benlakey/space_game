package org.seattlegamer.spacegame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Game {
	
	private static Logger logger = Logger.getLogger(Game.class);

	public static void main(String[] args) {

		BasicConfigurator.configure();

		logger.info("Initializing game settings.");
		GameSettings settings = new GameSettings();
		
		logger.info("Initializing game window.");
		GameCanvas canvas = new WindowedGameCanvas(settings.getTitle());
		
		logger.info("Initializing game engine.");
		Engine engine = new Engine(canvas);
		
		logger.info("Launching game.");
		engine.run();
		
	}
}
