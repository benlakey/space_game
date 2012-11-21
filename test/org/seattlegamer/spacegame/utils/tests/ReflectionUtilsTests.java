package org.seattlegamer.spacegame.utils.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.seattlegamer.spacegame.utils.ReflectionUtils;

public class ReflectionUtilsTests {

	@Test
	public void validClassCanBeSafeCast() {
		Foo foo = new Bar();
		Bar bar = ReflectionUtils.as(foo, Bar.class);
		assertNotNull(bar);
	}
	
	@Test
	public void invalidClassResultsInNull() {
		Foo foo = new Bar();
		Baz baz = ReflectionUtils.as(foo, Baz.class);
		assertNull(baz);
	}
	
	private class Foo {}
	private class Bar extends Foo {}
	private class Baz {}
	
}
