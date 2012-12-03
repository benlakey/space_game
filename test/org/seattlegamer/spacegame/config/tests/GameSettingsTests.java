package org.seattlegamer.spacegame.config.tests;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;
import org.seattlegamer.spacegame.config.GameSettings;

public class GameSettingsTests {
	
	private static final String GAME_TITLE = "Foo";
	private static final Integer TARGET_FRAMERATE = 90;
	
	private static final Properties testProperties;
	
	static {
		testProperties = new Properties();
		testProperties.put("title", GAME_TITLE);
		testProperties.put("target_framerate", TARGET_FRAMERATE.toString());
	}

	@Test
	public void gameSettingsPullsTitleFromProperties() {
		GameSettings settings = new GameSettings(testProperties);
		String title = settings.getTitle();
		assertEquals(GAME_TITLE, title);
	}
	
	@Test
	public void gameSettingsPullsTargetFramerateFromProperties() {
		GameSettings settings = new GameSettings(testProperties);
		Integer targetFramerate = settings.getTargetFramerate();	
		assertEquals(TARGET_FRAMERATE, targetFramerate);
	}
}
