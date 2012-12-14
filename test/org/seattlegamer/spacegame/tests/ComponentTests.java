package org.seattlegamer.spacegame.tests;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentBus;

public class ComponentTests {

	@Test
	public void toStringHasClassName() {
		ComponentBus bus = new ComponentBus();
		Component component = new TestComponent(bus, UUID.randomUUID());
		String string = component.toString();
		assertEquals("TestComponent", string);
	}
	
	private class TestComponent extends Component {
		
		public TestComponent(ComponentBus bus, UUID entityId) {
			super(bus, entityId);
		}

	}
	
}
