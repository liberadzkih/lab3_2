package edu.iis.mto.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock
    private Clock clock;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(clock);
    }

    @Test
    void confirmWithin24HoursTimeShouldNotThrowException() {
        when(clock.instant()).thenReturn(Instant.now().plus(6, ChronoUnit.HOURS));
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    void confirmAfter24HoursTimeShouldThrowException() {
        when(clock.instant()).thenReturn(Instant.now().plus(30, ChronoUnit.HOURS));
        order.submit();
        assertThrows(OrderExpiredException.class, order::confirm);
    }
}