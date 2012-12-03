package org.seattlegamer.spacegame.config;

import java.util.Properties;

import org.seattlegamer.spacegame.PropertiesLoader;
import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class GameSettings {

	private static final String PROPERTIES_FILE_PATH = "/spacegame.properties";

	private static GameSettings instance;
	
	public static GameSettings current() {
		if(instance == null) {
			instance = new GameSettings(PropertiesLoader.loadProperties(PROPERTIES_FILE_PATH));
		}
		return instance;
	}

	private final PropertiesAccessor propertiesAccessor;

	public GameSettings(Properties properties) {
		this.propertiesAccessor = new PropertiesAccessor(properties);
	}

	public String getTitle() {
		return this.propertiesAccessor.getString("title", "Space Game!");
	}

	public int getTargetFramerate() {
		return this.propertiesAccessor.getInteger("target_framerate", 100);
	}

	public String getFont() {
		return this.propertiesAccessor.getString("font", "Courier");
	}

	public int getDisplayModeWidth() {
		return this.propertiesAccessor.getInteger("displaymode.width", 800);
	}

	public int getDisplayModeHeight() {
		return this.propertiesAccessor.getInteger("displaymode.height", 600);
	}

	public int getDisplayModeBitDepth() {
		return this.propertiesAccessor.getInteger("displaymode.bit_depth", 16);
	}

	public int getDisplayModeRefreshRate() {
		return this.propertiesAccessor.getInteger("displaymode.refresh_rate", 60);
	}
	
	public int getScale() {
		int displayModeWidth = this.getDisplayModeWidth();
		int scaleValue = this.propertiesAccessor.getInteger("scale", 40);
		return displayModeWidth / scaleValue;
    }

}
