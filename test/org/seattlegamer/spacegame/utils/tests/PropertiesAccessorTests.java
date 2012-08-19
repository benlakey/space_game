package org.seattlegamer.spacegame.utils.tests;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;
import org.seattlegamer.spacegame.utils.PropertiesAccessor;

public class PropertiesAccessorTests {

	private static final String FOO_STRING_KEY = "foo_string";
	private static final String FOO_INTEGER_KEY = "foo_integer";
	private static final String FOO_BOOLEAN_KEY = "foo_boolean";
	
	private static final String FOO_STRING = "Foo";
	private static final Integer FOO_INTEGER = 90;
	private static final Boolean FOO_BOOLEAN = true;
	
	private static final Properties testProperties;
	
	static {
		testProperties = new Properties();
		testProperties.put(FOO_STRING_KEY, FOO_STRING);
		testProperties.put(FOO_INTEGER_KEY, FOO_INTEGER.toString());
		testProperties.put(FOO_BOOLEAN_KEY, FOO_BOOLEAN.toString());
	}
	
	@Test
	public void canAccessStrings() {

		PropertiesAccessor accessor = new PropertiesAccessor(testProperties);
		String fooStringRetrieved = accessor.getString(FOO_STRING_KEY, "");
		assertEquals(FOO_STRING, fooStringRetrieved);
		
	}
	
	@Test
	public void canAccessIntegers() {

		PropertiesAccessor accessor = new PropertiesAccessor(testProperties);
		Integer fooIntegerRetrieved = accessor.getInteger(FOO_INTEGER_KEY, 123);
		assertEquals(FOO_INTEGER, fooIntegerRetrieved);
		
	}
	
	@Test
	public void canAccessBooleans() {

		PropertiesAccessor accessor = new PropertiesAccessor(testProperties);
		boolean fooBooleanRetrieved = accessor.getBoolean(FOO_BOOLEAN_KEY, false);
		assertEquals(FOO_BOOLEAN, fooBooleanRetrieved);
		
	}
	
}
