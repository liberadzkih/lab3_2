package edu.iis.mto.time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;

public class OrderTest {

    private Order order;

    @Mock
    private Clock mockClock;

    @Before
    public void setUp() {
        mockClock = Mockito.mock(Clock.class);
        order = new Order(mockClock);
    }

    @Test
    public void confirm_OrderNotExpired_NotAtValidTime(){
        Mockito.when(mockClock.instant()).thenReturn(Instant.now());
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_OrderNotExpired_AtValidTime(){
        Mockito.when(mockClock.instant()).thenReturn(Instant.now(),Instant.now().plus(Duration.ofHours(24)));
        order.submit();
        order.confirm();
    }

    @Test(expected = OrderExpiredException.class)
    public void confirm_OrderExpired(){
        Mockito.when(mockClock.instant()).thenReturn(Instant.now(),Instant.now().plus(Duration.ofHours(25)));
        order.submit();
        order.confirm();
    }

}