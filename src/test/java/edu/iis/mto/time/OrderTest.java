package edu.iis.mto.time;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
    private Clock clock = mock(Clock.class);
    private Order order;
    @Before
    public void setUp() {
        order = new Order(clock);
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowExceptionWhenOrderExpired() {
        Instant submissionDate = Instant.now();
        Instant expiredSubmissionDate = submissionDate.plus(Duration.ofHours(25));
        when(clock.instant()).thenReturn(submissionDate, expiredSubmissionDate);
        order.submit();
        order.confirm();
    }

    @Test
    public void shouldConfirmOrderWhenCorrectTime() {
        Instant submissionDate = Instant.now();
        Instant expiredSubmissionDate = submissionDate.plus(Duration.ofHours(24));
        when(clock.instant()).thenReturn(submissionDate, expiredSubmissionDate);
        order.submit();
        order.confirm();
    }

    @Test
    public void shouldConfirmOrderWhenTheSameTimeGiven() {
        Instant submissionDate = Instant.now();
        when(clock.instant()).thenReturn(submissionDate, submissionDate);
        order.submit();
        order.confirm();
    }
}
