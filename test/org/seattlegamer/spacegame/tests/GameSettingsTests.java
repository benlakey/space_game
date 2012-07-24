package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.seattlegamer.spacegame.GameSettings;

public class GameSettingsTests {

	@Test
	public void testFoo1() {

		GameSettings settings = new GameSettings();
		assertEquals("Space Game!", settings.getTitle());
		
	}
}
