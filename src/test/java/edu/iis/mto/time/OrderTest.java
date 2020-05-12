package edu.iis.mto.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderTest {
    private Clock clock;
    private Order order;

    @BeforeEach
    void setUp() {
        clock = mock(Clock.class);
        order = new Order(clock);
    }

    @Test
    void testConfirmBeforeExpireException() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(28, ChronoUnit.HOURS));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    void testConfirmEqualsExpireSuccess() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(24, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    void testConfirmEqualsSubmissionSuccess() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now());
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    void testExpireBeforeConfirmSuccess() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().minus(10, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    void testConfirmBeforeExpireSuccess() {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(10, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }
}