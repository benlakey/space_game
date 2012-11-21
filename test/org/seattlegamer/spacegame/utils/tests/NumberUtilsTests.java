package org.seattlegamer.spacegame.utils.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.seattlegamer.spacegame.utils.NumberUtils;

public class NumberUtilsTests {

	@Test
	public void clampRestrictsIntegerLowerBound() {
		final int lowerBound = 45;
		final int number = 42;
		final int clamped = NumberUtils.clamp(number, lowerBound, 50);
		assertEquals(lowerBound, clamped);
	}
	
	@Test
	public void clampRestrictsIntegerUpperBound() {
		final int upperBound = 10;
		final int number = 14;
		final int clamped = NumberUtils.clamp(number, 0, upperBound);
		assertEquals(upperBound, clamped);
	}
	
	@Test
	public void clampLeavesNumberInRangeUnchanged() {
		final int number = 5;
		final int clamped = NumberUtils.clamp(number, 0, 10);
		assertEquals(number, clamped);
	}
	
	@Test
	public void canWrapNothingByNothing() {
		int wrapped = NumberUtils.wrap(0, 0);
		assertEquals(0, wrapped);
	}
	
	@Test
	public void canWrapSomethingByNothing() {
		int wrapped = NumberUtils.wrap(2, 0);
		assertEquals(0, wrapped);
	}
	
	@Test
	public void canWrapSomethingBySomething() {
		int wrapped = NumberUtils.wrap(3, 2);
		assertEquals(1, wrapped);
	}
	
	@Test
	public void numberSmallerThanWrapIsNotWrapped() {
		int wrapped = NumberUtils.wrap(2, 3);
		assertEquals(2, wrapped);
	}
	
}
