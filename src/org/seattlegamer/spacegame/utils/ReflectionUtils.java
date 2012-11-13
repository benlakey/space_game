package org.seattlegamer.spacegame.utils;

public class ReflectionUtils {

	public static <T> T as(Object o, Class<T> clazz){
		if(clazz.isInstance(o)){
			return clazz.cast(o);
		}
		return null;
	}
	
}
