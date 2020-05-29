package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    Order order;
    @Mock
    Clock clock;
    Instant instant;

    @Before
    public void init() {
        order = new Order(clock);
        instant = Instant.now();
    }

    @Test(expected = OrderExpiredException.class)
    public void confirm_shouldThrowsException_DurationGreaterThanValidPeriodTime() {
        mockClockInstant(instant.plus(Duration.ofHours(28)));
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_shouldNotThrowsException_DurationEqualsValidPeriodTime() {
        mockClockInstant(instant.plus(Duration.ofHours(24)));
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_shouldNotThrowsException_DurationEqualsSubmissionTime() {
        mockClockInstant(instant);
        order.submit();
        order.confirm();
    }

    @Test
    public void confirm_shouldNotThrowsException_DurationLessThanValidPeriodTime() {
        mockClockInstant(instant.plus(Duration.ofHours(10)));
        order.submit();
        order.confirm();
    }

    private void mockClockInstant(Instant secondInstant) {
        when(clock.instant()).thenReturn(instant, secondInstant);
    }

}
