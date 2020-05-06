import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {
    private Clock clock;
    private Order order;

    @BeforeEach
    public void init() {
        clock = Mockito.mock(Clock.class);
        order = new Order(clock);
    }

    @Test
    public void should_ReturnOrderExpiredException_30HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(30)));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    public void should_ReturnOrderExpiredException_25HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(25)));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    public void should_Not_ReturnOrderExpiredException_18HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(18)));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void should_Not_ReturnOrderExpiredException_10HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(10)));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void should_Not_ReturnOrderExpiredException_24HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(24)));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void should_Not_ReturnOrderExpiredException_2HoursAfterSubmit() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(2)));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }
}
