package org.seattlegamer.spacegame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.seattlegamer.spacegame.config.DependencyConfig;
import org.seattlegamer.spacegame.config.GameSettings;
import org.seattlegamer.spacegame.utils.PropertiesAccessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Game {

	private static Logger logger = Logger.getLogger(Game.class);
	private static final String PROPERTIES_FILE_PATH = "/spacegame.properties";
	
	public static void main(String[] args) {

		bootstrapConfig();

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(DependencyConfig.class);
		applicationContext.refresh();
		
		logger.info("Initializing game engine.");

		Engine engine = applicationContext.getBean(Engine.class);
		engine.run();

	}
	
	private static void bootstrapConfig() {
		Properties properties = loadProperties();
		PropertyConfigurator.configure(properties);
		PropertiesAccessor propertiesAccessor = new PropertiesAccessor(properties);
		GameSettings.initialize(propertiesAccessor);
	}
	
	private static Properties loadProperties() {
		
		InputStream in = Game.class.getResourceAsStream(PROPERTIES_FILE_PATH);

		try {
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load properties from " + PROPERTIES_FILE_PATH, e);
		} finally {
			if(in != null) {
				try { 
					in.close(); 
				} catch(IOException closeEx) {
					logger.warn("Couldn't close game settings property file.");
				}
			}
		}
		
	}
}
