import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class OrderTests {
    private Clock clock;
    private Order order;

    @BeforeEach
    public void init() {
        clock = Mockito.mock(Clock.class);
        order = new Order(clock);
    }

    @Test
    public void confirmBeforeOrderExpired_success() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(10, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirmAfterOrderExpired_throwException() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(30, ChronoUnit.HOURS));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    public void confirmAtHourOfExpiration_success() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(24, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirmAtTimeOfSubmit_success() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now());
        order.submit();
        assertDoesNotThrow(order::confirm);
    }
}
