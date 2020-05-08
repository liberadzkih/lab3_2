package edu.iis.mto.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    Order order;
    @Mock //stworzy dublera
    Clock clock;
   
    @BeforeEach //przed kazdym testem przygotuj nowe dane
    public void setUp(){
        order = new Order(clock);
    }

    @Test void dataExpired_shouldThrowOrderExpiredExecption() {
        Instant now = Instant.now(); //gets the current instant of the clock.
        Instant later = Instant.now().plus(Duration.ofHours(30)); //po 24 h "wygasa"
        when(clock.instant()).thenReturn(now,later);
        order.submit(); //confirm wymaga bycia submitted
        assertThrows(OrderExpiredException.class,order::confirm); //:: method reference 
    }
}
