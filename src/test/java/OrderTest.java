import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    private Clock getClockMockWithConfirmTimeOffsetBy(Duration offsetDuration) {
        Clock clock = mock(Clock.class);
        Instant submitTime = Instant.now();
        Instant confirmTime = submitTime.plus(offsetDuration);
        when(clock.instant()).thenReturn(submitTime, confirmTime);
        return clock;
    }

    @Test
    public void orderNotExpiredInstantly() {
        Clock clock = getClockMockWithConfirmTimeOffsetBy(Duration.ofHours(0));
        Order order = new Order(clock);
        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredAfterHoursLessThanValidPeriod() {
        Clock clock = getClockMockWithConfirmTimeOffsetBy(Duration.ofHours(8));
        Order order = new Order(clock);
        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredOnValidPeriod() {
        Clock clock = getClockMockWithConfirmTimeOffsetBy(Duration.ofHours(24));
        Order order = new Order(clock);
        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderExpiredOnHoursMoreThanValidPeriod() {
        Clock clock = getClockMockWithConfirmTimeOffsetBy(Duration.ofHours(25));
        Order order = new Order(clock);
        order.submit();

        assertThrows(OrderExpiredException.class, order::confirm);
    }

}
