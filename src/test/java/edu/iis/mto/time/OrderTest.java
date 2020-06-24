package edu.iis.mto.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Equals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderTest {

    Clock clockMock = mock(Clock.class);
    private Order order = new Order(clockMock);

    @Test()
    public void throws_exception_when_order_is_expired() {
        Instant now = Instant.now();

        Instant after= Instant.now()
                              .plus(Duration.ofHours(25));

        Mockito.when(clockMock.instant())
               .thenReturn(now, after);

        order.submit();

        Assertions.assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

    @Test
    public void throws_exception_when_order_in_wrong_state() {
        Assertions.assertThrows(OrderStateException.class, () -> order.confirm());
    }

    @Test
    public void no_throws_exception_when_hour_order_is_submitted_after_day() {
        Instant now = Instant.now();
        Instant after= Instant.now()
                              .plus(Duration.ofHours(24));

        Mockito.when(clockMock.instant())
               .thenReturn(now, after);

        order.submit();

        Assertions.assertDoesNotThrow(() -> order.confirm());
    }

    @Test()
    public void no_throws_exception_when_order_is_submitted_but_not_expired() {
        Instant now = Instant.now();

        Instant after= Instant.now()
                              .plus(Duration.ofHours(5));

        Mockito.when(clockMock.instant())
               .thenReturn(now, after);
        order.submit();

        Assertions.assertDoesNotThrow(() -> order.confirm());
    }

    @Test()
    public void no_throws_exception_when_order_is_not_expired() {
        Instant now = Instant.now();
        Instant after= Instant.now().plus(Duration.ofHours(0));
        Mockito.when(clockMock.instant())
               .thenReturn(now, after);
        order.submit();

        Assertions.assertDoesNotThrow(() -> order.confirm());
    }

}
