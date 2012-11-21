package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.seattlegamer.spacegame.Component;
import org.seattlegamer.spacegame.Entity;
import org.seattlegamer.spacegame.Handler;
import org.seattlegamer.spacegame.Input;

public class EntityTests {

	@Test
	public void canAddComponents() {
		Entity entity = new Entity();
		TestComponent component = new TestComponent(entity);
		entity.add(component);
		assertEquals(entity, component.getOwner());
	}
	
	@Test
	public void canUpdateComponents() {
		
		Entity entity = new Entity();
		TestComponent component = new TestComponent(entity);
		entity.add(component);
		
		assertFalse(component.wasUpdated());
		entity.update(null, 0);
		assertTrue(component.wasUpdated());
		
	}
	
	private class TestComponent extends Component {

		private boolean updated;
		
		public TestComponent(Handler owner) {
			super(owner);
		}
		
		@Override
		public void update(Input input, long elapsedMillis) {
			this.updated = true;
		}
		
		public Entity getOwner() {
			return (Entity)this.owner;
		}
		
		public boolean wasUpdated() {
			return this.updated;
		}
		
	}
	
}
