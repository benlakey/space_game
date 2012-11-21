package org.seattlegamer.spacegame.utils;

public final class NumberUtils {
	
	private NumberUtils() {}

	public static <T extends Number & Comparable<T>> T clamp(T val, T min, T max) {
		
		if(val.compareTo(min) < 0) {
			return min;
		}
		
		if(val.compareTo(max) > 0) {
			return max;
		}
		
		return val;
		
	}
	
	public static int wrap(int index, int size) {
		if(size == 0) {
			return 0;
		}
		return (index % size + size) % size;
	}

}
