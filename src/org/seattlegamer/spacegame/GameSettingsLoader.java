package org.seattlegamer.spacegame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class GameSettingsLoader {
	
	private static final Logger logger = Logger.getLogger(GameSettingsLoader.class);

	public static GameSettings loadFromFile(String filePath) {

		InputStream in = GameSettingsLoader.class.getResourceAsStream(filePath);
		
		Properties properties = new Properties();
		
		try {
			
			properties.load(in);
			PropertiesAccessor accessor = new PropertiesAccessor(properties);
			return new GameSettings(accessor);
			
		} catch(IOException ex) {
			logger.warn(String.format("Couldn't load game settings from %s", filePath));
		} finally {

			if(in != null) {
				try { 
					in.close(); 
				} catch(IOException closeEx) {
					logger.warn("Couldn't close game settings property file.");
				}
			}

		}
		
		return null;

	}
	
}
