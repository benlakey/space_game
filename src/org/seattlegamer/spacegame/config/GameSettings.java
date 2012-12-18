package org.seattlegamer.spacegame.config;

import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class GameSettings {

	private final PropertiesAccessor propertiesAccessor;

	public GameSettings(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
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
		int scaleValue = this.propertiesAccessor.getInteger("scale", 8);
		return displayModeWidth / scaleValue;
    }

}
