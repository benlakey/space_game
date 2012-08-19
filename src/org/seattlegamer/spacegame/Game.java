package org.seattlegamer.spacegame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Game {

	private static final String PROPERTIES_FILE_PATH = "/spacegame.properties";
	
	private static Logger logger = Logger.getLogger(Game.class);
	
	public static void main(String[] args) {

		BasicConfigurator.configure();

		GameSettings settings = GameSettingsLoader.loadFromFile(PROPERTIES_FILE_PATH);
		RateLimiter rateLimiter = new RateLimiter(settings.getTargetFramerate());
		GameCanvas canvas = new WindowedGameCanvas(settings.getTitle());
		CanvasRenderer renderer = new CanvasRenderer(canvas);

		logger.info("Initializing game engine.");
		Engine engine = new Engine(renderer, rateLimiter);

		engine.run();
		
	}
}
