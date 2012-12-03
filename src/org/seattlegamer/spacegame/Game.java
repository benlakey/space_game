package org.seattlegamer.spacegame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.seattlegamer.spacegame.config.DependencyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Game {

	private static Logger logger = Logger.getLogger(Game.class);
	private static final String PROPERTIES_FILE_PATH = "/spacegame.properties";
	
	public static void main(String[] args) throws InterruptedException {

		PropertyConfigurator.configure(PropertiesLoader.loadProperties(PROPERTIES_FILE_PATH));

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(DependencyConfig.class);
		applicationContext.refresh();
		
		logger.info("Initializing game engine.");

		Engine engine = applicationContext.getBean(Engine.class);
		engine.run();

	}

}
