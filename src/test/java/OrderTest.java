import edu.iis.mto.time.FakeClock;
import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Clock;

public class OrderTest {

    Order order;
    Clock fakeClock;

    @Test
    public void callConfirmMethod_OrderExpiredExceptionTest() {
        fakeClock = new FakeClock(30);
        order = new Order(fakeClock);
        order.submit();
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

}
