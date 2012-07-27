package org.seattlegamer.spacegame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GameSettings {
	
	private static final Logger logger = Logger.getLogger(GameSettings.class);
	private static final String propertiesFilename = "/spacegame.properties";
	private static final String defaultTitle = "Space Game";

	private final Properties properties;
	
	public GameSettings() {
		
		this.properties = new Properties();

		InputStream in = this.getClass().getResourceAsStream(propertiesFilename);
		
		try {
			this.properties.load(in);
		} catch(IOException ex) {
			logger.warn(String.format("Couldn't load game settings from %s", propertiesFilename));
		} finally {
			this.closePropertiesFileStream(in);
		}

	}

	private void closePropertiesFileStream(InputStream in) {
		
		if(in == null) {
			return;
		}
		
		try { 
			in.close(); 
		} catch(IOException closeEx) {
			logger.warn(String.format("Couldn't close %s", propertiesFilename));
		}
	}
	
	public String getTitle() {
		return this.properties.getProperty("title", defaultTitle);
	}

}
