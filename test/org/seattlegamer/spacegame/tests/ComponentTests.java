package org.seattlegamer.spacegame.tests;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.BusImpl;
import org.seattlegamer.spacegame.core.Component;

public class ComponentTests {

	@Test
	public void toStringHasClassName() {
		Bus bus = new BusImpl();
		Component component = new TestComponent(bus, UUID.randomUUID());
		String string = component.toString();
		assertEquals("TestComponent", string);
	}
	
	private class TestComponent extends Component {
		
		public TestComponent(Bus bus, UUID entityId) {
			super(bus, entityId);
		}

	}
	
}
