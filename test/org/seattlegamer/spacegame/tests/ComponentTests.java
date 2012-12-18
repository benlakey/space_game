package org.seattlegamer.spacegame.tests;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.ComponentBus;

public class ComponentTests {

	@Test
	public void toStringHasClassName() {
		Bus<Component> bus = new ComponentBus();
		Component component = new TestComponent(bus, UUID.randomUUID());
		String string = component.toString();
		assertEquals("TestComponent", string);
	}
	
	private class TestComponent extends Component {
		
		public TestComponent(Bus<Component> bus, UUID entityId) {
			super(bus, entityId);
		}

	}
	
}
