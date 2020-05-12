package edu.iis.mto.time;

import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class OrderTest {
    private Order order;
    private Clock clock;

    @BeforeEach
    public void setUp() {
        clock = Mockito.mock(Clock.class);
        order = new Order(clock);
    }

    private void getClockMockWithConfirmTimeOffsetBy(int offsetDuration) {
        when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(offsetDuration, ChronoUnit.HOURS));
    }

    @Test
    public void orderNotExpiredInstantly() {
        getClockMockWithConfirmTimeOffsetBy(0);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredAfterHoursLessThanValidPeriod() {
        getClockMockWithConfirmTimeOffsetBy(8);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderNotExpiredOnValidPeriod() {
        getClockMockWithConfirmTimeOffsetBy(24);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    public void orderExpiredOnHoursMoreThanValidPeriod() {
        getClockMockWithConfirmTimeOffsetBy(25);
        order.submit();

        assertThrows(OrderExpiredException.class, order::confirm);
    }

}