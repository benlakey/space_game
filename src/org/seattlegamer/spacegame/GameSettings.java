package org.seattlegamer.spacegame;

import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class GameSettings {

	private static final String KEY_TITLE = "title";
	private static final String KEY_TARGET_FRAMERATE = "target_framerate";
	
	private static final String DEFAULT_TITLE = "Space Game!";
	private static final int DEFAULT_TARGET_FRAMERATE = 100;

	private final PropertiesAccessor propertiesAccessor;
	
	public GameSettings(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
	}

	public String getTitle() {
		return this.propertiesAccessor.getString(KEY_TITLE, DEFAULT_TITLE);
	}

	public int getTargetFramerate() {
		return this.propertiesAccessor.getInteger(KEY_TARGET_FRAMERATE, DEFAULT_TARGET_FRAMERATE);
	}

}
