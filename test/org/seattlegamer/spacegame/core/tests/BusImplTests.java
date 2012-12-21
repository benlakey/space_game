package org.seattlegamer.spacegame.core.tests;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.seattlegamer.spacegame.core.Bus;
import org.seattlegamer.spacegame.core.BusImpl;
import org.seattlegamer.spacegame.core.Component;
import org.seattlegamer.spacegame.core.Subscription;

public class BusImplTests {

	@Test
	public void canSendToSpecificEntityId() {
		
		BusImpl bus = new BusImpl();
		
		UUID entityId = UUID.randomUUID();
		
		Foo fooOne = new Foo(bus, entityId);
		Foo fooTwo = new Foo(bus, UUID.randomUUID());
		
		bus.register(fooOne, entityId);
		bus.send("blah", entityId);
		
		assertEquals("blah", fooOne.fooValue);
		assertEquals(null, fooTwo.fooValue);

	}
	
	@Test
	public void canRegisterAndDeregisterComponentsWithBus() {
		
		Bus bus = new BusImpl();
		
		UUID entityId = UUID.randomUUID();
		
		Foo fooOne = new Foo(bus, entityId);
		
		bus.register(fooOne, entityId);
		bus.send("blah", entityId);
		
		assertEquals("blah", fooOne.fooValue);
		fooOne.fooValue = null;

		bus.deregister(fooOne, entityId);
		
		assertEquals(null, fooOne.fooValue);

	}
	
	@Test
	public void unsubscribedMessagesBroadcastNowhereSilently() {
		
		Bus bus = new BusImpl();
		bus.broadcast("buzz");
		
	}
	
	@Test
	public void registrationsTakeIntoAccountSuperClass() {
		
		Bus bus = new BusImpl();
		
		UUID entityId = UUID.randomUUID();
		
		Bar bar = new Bar(bus, entityId);
		
		bus.register(bar, entityId);
		bus.broadcast("blah");
		
		assertEquals("blah", bar.fooValue);
		assertEquals("blah", bar.barValue);

	}
	
	public class Foo extends Component {
		
		public String fooValue;
		
		public Foo(Bus bus, UUID entityId) {
			super(bus, entityId);
		}

		@Subscription
		public void fooMethod(String string) {
			fooValue = string;
		}
		
	}
	
	public class Bar extends Foo {

		public String barValue;
		
		public Bar(Bus bus, UUID entityId) {
			super(bus, entityId);
		}
		
		@Subscription
		public void barMethod(String string) {
			barValue = string;
		}
		
	}
	
}
