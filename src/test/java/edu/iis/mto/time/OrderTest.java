package edu.iis.mto.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Clock clock;
    private Order order;

    @BeforeEach
    public void setup() {
        clock = Mockito.mock(Clock.class);
        order = new Order(clock);
    }

}