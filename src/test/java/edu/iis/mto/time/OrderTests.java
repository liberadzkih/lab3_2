package edu.iis.mto.time;

import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class OrderTests {
	private Clock clock;
	private Order order;
	
	@Before
	public void refresh() {
		clock = Mockito.mock(Clock.class);
		order = new Order(clock); 
		
		Mockito.when(clock.instant()).thenReturn(Instant.now());
	}
	
	@Test(expected = OrderExpiredException.class)
	public void confirmAfterValidDate() {
		order.submit();
		
		Mockito.when(clock.instant()).thenReturn(Instant.now().plus(40, ChronoUnit.HOURS));
		order.confirm();
	}
	
	@Test
	public void confirmInValidDate() {
		order.submit();
		
		Mockito.when(clock.instant()).thenReturn(Instant.now().plus(4, ChronoUnit.HOURS));
		order.confirm();
	}
	
	@Test
	public void confirmWithPastDate() {
		order.submit();
		
		Mockito.when(clock.instant()).thenReturn(Instant.now().minus(40, ChronoUnit.HOURS));
		order.confirm();
	}
	
	@Test
	public void stateAfterSubmit() {
		order.submit();
		
		assertEquals(Order.State.SUBMITTED, order.getOrderState());
	}
	
	@Test
	public void stateAfterSubmitAndConfirm() {
		order.submit();
		order.confirm();
		
		assertEquals(Order.State.CONFIRMED, order.getOrderState());
	}
	
	@Test
	public void stateAfterSubmitAndConfirmAndRealize() {
		order.submit();
		order.confirm();
		order.realize();
		
		assertEquals(Order.State.REALIZED, order.getOrderState());
	}
}
