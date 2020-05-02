package edu.iis.mto.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class OrderTests {

    @Test
    public void confirm_ExceededValidPeriodTime_OrderExpiredExceptionThrown() {
        Clock clock = Mockito.mock(Clock.class);

        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(25));

        Mockito.when(clock.instant()).thenReturn(before, after);

        Order order = new Order(clock);
        order.submit();

        Assertions.assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    public void confirm_PeriodTimeEqualToValidPeriodTime_NothingShouldBeThrown() {
        Clock clock = Mockito.mock(Clock.class);

        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(24));

        Mockito.when(clock.instant()).thenReturn(before, after);

        Order order = new Order(clock);
        order.submit();

        Assertions.assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_PeriodTimeLessThanValidPeriodTime_NothingShouldBeThrown() {
        Clock clock = Mockito.mock(Clock.class);

        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(23));

        Mockito.when(clock.instant()).thenReturn(before, after);

        Order order = new Order(clock);
        order.submit();

        Assertions.assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_PeriodTimeEqualToZero_NothingShouldBeThrown() {
        Clock clock = Mockito.mock(Clock.class);

        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(0));

        Mockito.when(clock.instant()).thenReturn(before, after);

        Order order = new Order(clock);
        order.submit();

        Assertions.assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_ConfirmDateEarlierThanSubmitDate_NothingShouldBeThrown() {
        Clock clock = Mockito.mock(Clock.class);

        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(23));

        Mockito.when(clock.instant()).thenReturn(after, before);

        Order order = new Order(clock);
        order.submit();

        Assertions.assertDoesNotThrow(order::confirm);
    }

}
