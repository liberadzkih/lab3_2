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

    private Clock fakeClock;
    private Order order;

    @BeforeEach void setUp() {
        fakeClock = mock(Clock.class);
        order = new Order();
        order.setCustomClock(fakeClock);
    }

    @Test void testWhenConfirmationIsPastTheExpirationDate_expectOrderExpiredException() {
        when(fakeClock.instant()).thenReturn(Instant.now(), Instant.now().plus(25, ChronoUnit.HOURS));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test void testWhenConfirmationIsBeforeTheExpirationDate_expectSuccess() {
        when(fakeClock.instant()).thenReturn(Instant.now(), Instant.now().plus(12, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test void testWhenConfirmationIsEqualToExpirationDate_expectSuccess() {
        when(fakeClock.instant()).thenReturn(Instant.now(), Instant.now().plus(24, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test void testWhenConfirmationTimeIsEqualToSubmissionTime_expectSuccess() {
        when(fakeClock.instant()).thenReturn(Instant.now(), Instant.now());
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test void testWhenExpirationDateIsBeforeTheConfirmation_expectSuccess() {
        when(fakeClock.instant()).thenReturn(Instant.now(), Instant.now().minus(12, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }
}
