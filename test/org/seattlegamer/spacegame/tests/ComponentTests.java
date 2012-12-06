package org.seattlegamer.spacegame.tests;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.Bus;
import org.seattlegamer.spacegame.Component;

public class ComponentTests {

	@Test
	public void toStringHasClassName() {
		Component component = new TestComponent(null, UUID.randomUUID());
		String string = component.toString();
		assertEquals("TestComponent", string);
	}
	
	private class TestComponent extends Component {
		
		public TestComponent(Bus bus, UUID entityId) {
			super(bus, entityId);
		}

		@Override
		public void registerHandlers() {
		}

		@Override
		public void deregisterHandlers() {
		}

	}
	
}
