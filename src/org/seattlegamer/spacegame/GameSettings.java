package org.seattlegamer.spacegame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GameSettings {
	
	private static final Logger logger = Logger.getLogger(GameSettings.class);
	private static final String defaultPropertiesFilePath = "/spacegame.properties";
	private static final String defaultTitle = "Space Game!";
	private static final int defaultTargetFramerate = 100;

	private final Properties properties;
	
	public GameSettings() {
		this.properties = new Properties();
	}
	
	public void loadFromFile() {
		this.loadFromFile(defaultPropertiesFilePath);
	}
	
	public void loadFromFile(String filePath) {

		InputStream in = this.getClass().getResourceAsStream(filePath);
		
		try {
			this.properties.load(in);
		} catch(IOException ex) {
			logger.warn(String.format("Couldn't load game settings from %s", filePath));
		} finally {
			closePropertiesFileStream(in);
		}

	}

	private static void closePropertiesFileStream(InputStream in) {
		
		if(in == null) {
			return;
		}
		
		try { 
			in.close(); 
		} catch(IOException closeEx) {
			logger.warn("Couldn't close game settings property file.");
		}
	}
	
	public String getTitle() {
		return this.properties.getProperty("title", defaultTitle);
	}
	
	public int getTargetFramerate() {

		String targetFramerateStr = this.properties.getProperty("target_framerate");
		if(targetFramerateStr == null) { 
			return defaultTargetFramerate;
		}
		
		int targetFramerate = NumberUtil.IntegerUtil.tryParse(targetFramerateStr);
		if(targetFramerate == 0) {
			return defaultTargetFramerate;
		}
		
		return targetFramerate;
	}

}
