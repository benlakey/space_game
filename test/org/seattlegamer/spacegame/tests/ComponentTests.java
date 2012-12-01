package org.seattlegamer.spacegame.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.ComponentGroup;
import org.seattlegamer.spacegame.Entity;


public class ComponentTests {

	@Test
	public void canAddComponentToGroup() {
		ComponentGroup group = ComponentGroup.MENU;
		Component component = new TestComponent(null);
		component.setGroupMembership(group, true);
		assertTrue(component.isMember(group));
	}
	
	@Test
	public void canEnableComponent() {
		Component component = new TestComponent(null);
		component.setEnabled(true);
		assertTrue(component.isEnabled());
	}
	
	@Test
	public void canDisableComponent() {
		Component component = new TestComponent(null);
		component.setEnabled(false);
		assertFalse(component.isEnabled());
	}
	
	@Test
	public void toStringHasClassName() {
		Component component = new TestComponent(null);
		String string = component.toString();
		assertEquals("TestComponent", string);
	}
	
	private class TestComponent extends Component {

		public TestComponent(Entity owner) {
			super(owner);
		} 
		
	}
	
}
