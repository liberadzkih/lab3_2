package edu.iis.mto.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTests {

    private Clock clock;
    private Order order;

    @BeforeEach
    public void init() {
        clock = mock(Clock.class);
        order = new Order(clock);
    }

    @Test
    public void confirm_ExceededValidPeriodTime_OrderExpiredExceptionThrown() {
        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(25));

        when(clock.instant()).thenReturn(before, after);
        order.submit();

        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    public void confirm_PeriodTimeEqualToValidPeriodTime_NothingShouldBeThrown() {
        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(24));

        when(clock.instant()).thenReturn(before, after);

        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_PeriodTimeLessThanValidPeriodTime_NothingShouldBeThrown() {
        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(23));

        when(clock.instant()).thenReturn(before, after);

        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_PeriodTimeEqualToZero_NothingShouldBeThrown() {
        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(0));

        when(clock.instant()).thenReturn(before, after);

        order.submit();

        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void confirm_ConfirmDateEarlierThanSubmitDate_NothingShouldBeThrown() {
        Instant before = Instant.now();
        Instant after = before.plus(Duration.ofHours(23));

        when(clock.instant()).thenReturn(after, before);

        order.submit();

        assertDoesNotThrow(order::confirm);
    }

}
