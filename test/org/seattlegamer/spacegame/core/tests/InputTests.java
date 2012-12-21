package org.seattlegamer.spacegame.core.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Input;

public class InputTests {

	@Test
	public void keyPressesAreRecordedCorrectly() {
		
		int keyCode = KeyEvent.VK_SPACE;
		
		Input input = new Input();
		assertFalse(input.isKeyInputActive(keyCode));
		input.keyPressed(getMockKeyEvent(keyCode));
		assertTrue(input.isKeyInputActive(keyCode));

	}
	
	@Test
	public void keyReleasesAreRecordedCorrectly() {
		
		int keyCode = KeyEvent.VK_SPACE;

		Input input = new Input();
		input.keyPressed(getMockKeyEvent(keyCode));
		assertTrue(input.isKeyInputActive(keyCode));
		input.keyReleased(getMockKeyEvent(keyCode));
		assertFalse(input.isKeyInputActive(keyCode));
		
	}
	
	@Test
	public void mousePressesAreRecordedCorrectly() {

	}
	
	@Test
	public void mouseReleasesAreRecordedCorrectly() {
		
	}
	
	//TODO: use mockito
	private static KeyEvent getMockKeyEvent(int keyCode) {
		@SuppressWarnings("serial") Component awtComponent = new Component() {};
		return new KeyEvent(awtComponent, 0, 0L, 0, keyCode, (char)keyCode);
	}
	
}
