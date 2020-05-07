package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;


public class OrderTest {
    private Order order;
    @Mock
    private Clock clock;


    @Before
    public void setUp() {
        clock = Mockito.mock(Clock.class);
        order = new Order(clock);
    }

    @Test(expected = OrderExpiredException.class)
    public void confirm_OrderExpired1Hours() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(25)));
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_HasNotExpired() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(1)));
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_AtTimeOfExpiration() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now().plus(Duration.ofHours(24)));
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_NoOffset() {
        Mockito.when(clock.instant()).thenReturn(Instant.now(), Instant.now());
        order.submit();
        order.confirm();
    }
}