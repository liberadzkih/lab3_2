package edu.iis.mto.time;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class OrderTests {
	private Order order;
	
	@Before
	public void refresh() {
		order = new Order(); 
	}
	
	@Test(expected = OrderExpiredException.class)
	public void confirmAfterValidDate() {
		order.submit(DateTime.now().minusHours(40));
		order.confirm();
	}
	
	@Test
	public void confirmInValidDate() {
		order.submit(DateTime.now().minusHours(4));
		order.confirm();
	}
	
	@Test
	public void confirmWithFutureDate() {
		order.submit(DateTime.now().plusHours(40));
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
