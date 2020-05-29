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

}
