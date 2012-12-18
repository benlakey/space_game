package org.seattlegamer.spacegame.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesAccessor {
	
	private static Logger logger = Logger.getLogger(PropertiesAccessor.class);
	
	private final Properties properties;
	
	public PropertiesAccessor(Properties properties) {
		this.properties = properties;
	}
	
	public String getString(String key, String defaultValue) {
		
		String value = this.properties.getProperty(key);
		
		if(value == null) {
			return defaultValue;
		}

		return value.trim();
	}
	
	public int getInteger(String key, int defaultValue) {

		String valueAsString = this.properties.getProperty(key);
		
		if(valueAsString == null) {
			return defaultValue;
		}
		
		valueAsString = valueAsString.trim();
		
		try {
			return Integer.parseInt(valueAsString);
		} catch(NumberFormatException ex) {
			logger.warn(String.format("Unable to parse property value '%s' into an integer.", key), ex);
		}
		
		return defaultValue;
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		
		String valueAsString = this.properties.getProperty(key);
		
		if(valueAsString == null) {
			return defaultValue;
		}
		
		valueAsString = valueAsString.trim();
		
		if(valueAsString.equalsIgnoreCase("true") || valueAsString.equalsIgnoreCase("yes")) {
			return true;
		}
		
		if(valueAsString.equalsIgnoreCase("false") || valueAsString.equalsIgnoreCase("no")) {
			return false;
		}

		return defaultValue;
	}

}
