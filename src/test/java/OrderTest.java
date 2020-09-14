import edu.iis.mto.time.FakeClock;
import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;

public class OrderTest {

    Order order;
    Clock fakeClock;

    @Test
    public void makeOrder_hoursElapsedAfterSubmittionOver24_OrderExpiredExceptionTest() {
        fakeClock = new FakeClock(30);
        order = new Order(fakeClock);
        order.submit();
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

    @Test
    public void makeOrder_hoursElapsedAfterSubmittionBelow24() {
        for (int i = 0; i < 24; i++) {
            fakeClock = new FakeClock(i);
            order = new Order(fakeClock);
            order.submit();
            order.confirm();
            assertTrue(order.getOrderState() != Order.State.CANCELLED);
        }
    }
}
