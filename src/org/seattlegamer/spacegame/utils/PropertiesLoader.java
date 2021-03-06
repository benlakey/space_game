package org.seattlegamer.spacegame.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesLoader {
	
	private static Logger logger = Logger.getLogger(PropertiesLoader.class);

	private PropertiesLoader() {}
	
	public static Properties loadProperties(String path) {
		
		InputStream in = PropertiesLoader.class.getResourceAsStream(path);

		try {
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load properties from " + path, e);
		} finally {
			if(in != null) {
				try { 
					in.close(); 
				} catch(IOException closeEx) {
					logger.warn("Couldn't close property file.");
				}
			}
		}
		
	}
	
}
