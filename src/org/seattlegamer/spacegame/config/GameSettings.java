package org.seattlegamer.spacegame.config;

import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class GameSettings {

	private static final String KEY_TITLE = "title";
	private static final String KEY_TARGET_FRAMERATE = "target_framerate";
	
	private static final String DEFAULT_TITLE = "Space Game!";
	private static final int DEFAULT_TARGET_FRAMERATE = 100;

	private static PropertiesAccessor propertiesAccessor;
	
	public static void initialize(PropertiesAccessor accessor) {
		propertiesAccessor = accessor;
	}

	public static String getTitle() {
		ensureInitialized();
		return propertiesAccessor.getString(KEY_TITLE, DEFAULT_TITLE);
	}

	public static int getTargetFramerate() {
		ensureInitialized();
		return propertiesAccessor.getInteger(KEY_TARGET_FRAMERATE, DEFAULT_TARGET_FRAMERATE);
	}
	
	public static boolean shouldUseFullscreen() {
		ensureInitialized();
		return propertiesAccessor.getBoolean("fullscreen", true);
	}
	
	private static void ensureInitialized() {
		if(propertiesAccessor == null) {
			throw new IllegalStateException("In order to use GameSettings, you must first call initialize.");
		}
	}

}
