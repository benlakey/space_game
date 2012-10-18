package org.seattlegamer.spacegame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Game {

	private static Logger logger = Logger.getLogger(Game.class);
	
	public static void main(String[] args) {

		BasicConfigurator.configure();

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(DependencyConfig.class);
		applicationContext.refresh();
		
		logger.info("Initializing game engine.");
		
		Activity startActivity = new MainMenuActivity();
		
		Engine engine = applicationContext.getBean(Engine.class);
		engine.run(startActivity);

	}
}
