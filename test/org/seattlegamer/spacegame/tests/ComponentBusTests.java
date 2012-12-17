package org.seattlegamer.spacegame.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.ComponentBus;
import org.seattlegamer.spacegame.core.Subscription;

public class ComponentBusTests {

	@Test
	public void canSendToSpecificEntityId() {
		
		ComponentBus bus = new ComponentBus();
		
		UUID entityId = UUID.randomUUID();
		
		Foo fooOne = new Foo(bus, entityId);
		Foo fooTwo = new Foo(bus, UUID.randomUUID());
		
		bus.send("blah", entityId);
		
		assertTrue(fooOne.methodCalled);
		assertFalse(fooTwo.methodCalled);

	}
	
	@Test
	public void canRegisterAndDeregisterComponentsWithBus() {
		
		ComponentBus bus = new ComponentBus();
		
		Foo fooOne = new Foo(bus, UUID.randomUUID());
		bus.broadcast("fizz");
		assertTrue(fooOne.methodCalled);
		
		Foo fooTwo = new Foo(bus, UUID.randomUUID());
		fooTwo.setEnabled(false);
		bus.broadcast("fizz");
		assertFalse(fooTwo.methodCalled);

	}
	
	@Test
	public void messagesAreOnlyBroadcastToInstancesRegistered() {
		
		ComponentBus bus = new ComponentBus();
		
		Foo fooOne = new Foo(bus, UUID.randomUUID());
		Foo fooTwo = new Foo(bus, UUID.randomUUID());

		fooTwo.setEnabled(false);
		bus.broadcast("fizz");
		
		assertTrue(fooOne.methodCalled);
		assertFalse(fooTwo.methodCalled);
		
	}
	
	@Test
	public void unsubscribedMessagesBroadcastNowhereSilently() {
		
		ComponentBus bus = new ComponentBus();
		bus.broadcast("buzz");
		
	}
	
	public class Foo extends Component {
		
		public Foo(ComponentBus bus, UUID entityId) {
			super(bus, entityId);
		}

		public boolean methodCalled;
		
		@Subscription
		public void fooMethod(String string) {
			methodCalled = true;
		}
		
	}
	
}
