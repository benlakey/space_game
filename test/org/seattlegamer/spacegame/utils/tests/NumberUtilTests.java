package org.seattlegamer.spacegame.utils.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.seattlegamer.spacegame.utils.NumberUtil;

public class NumberUtilTests {

	@Test
	public void clampRestrictsIntegerLowerBound() {
		
		final int lowerBound = 45;
		final int number = 42;
		final int clamped = NumberUtil.clamp(number, lowerBound, 50);
		assertEquals(lowerBound, clamped);
		
	}
	
	@Test
	public void clampRestrictsIntegerUpperBound() {
		
		final int upperBound = 10;
		final int number = 14;
		final int clamped = NumberUtil.clamp(number, 0, upperBound);
		assertEquals(upperBound, clamped);
		
	}
	
	@Test
	public void clampLeavesNumberInRangeUnchanged() {

		final int number = 5;
		final int clamped = NumberUtil.clamp(number, 0, 10);
		assertEquals(number, clamped);
		
	}
	
}
