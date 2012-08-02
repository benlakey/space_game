package org.seattlegamer.spacegame;

public final class NumberUtil {
	
	private NumberUtil() {}

	public static <T extends Number & Comparable<T>> T clamp(T val, T min, T max) {
		
		if(val.compareTo(min) < 0) {
			return min;
		}
		
		if(val.compareTo(max) > 0) {
			return max;
		}
		
		return val;
		
	}
	
	public static class IntegerUtil {

		public static Integer tryParse(String text) {
			try {
				return new Integer(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}

	}
	
	public static class LongUtil {

		public static Long tryParse(String text) {
			try {
				return new Long(text);
			} catch (NumberFormatException e) {
				return 0L;
			}
		}

	}

}
