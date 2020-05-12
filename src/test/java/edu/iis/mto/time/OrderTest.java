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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @Test void thirtyHoursPass_dataExpired_shouldThrowOrderExpiredExecption() {
        Instant now = Instant.now(); //gets the current instant of the clock.
        Instant later = Instant.now().plus(Duration.ofHours(30)); //po 24 h "wygasa"
        when(clock.instant()).thenReturn(now,later);
        order.submit(); //confirm wymaga bycia submitted
        assertThrows(OrderExpiredException.class,order::confirm); //:: method reference
    }

    @Test void twentyFiveHoursPass_dataExpired_shouldThrowOrderExpiredExecption() {
        Instant now = Instant.now();
        Instant later = Instant.now().plus(Duration.ofHours(25));
        when(clock.instant()).thenReturn(now,later);
        order.submit();
        assertThrows(OrderExpiredException.class,order::confirm);
    }

    @Test void fourHoursPast_dataNotExpired_shouldNotThrowOrderExpiredExecption() {
        Instant now = Instant.now();
        Instant later = Instant.now().plus(Duration.ofHours(4));
        when(clock.instant()).thenReturn(now,later);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test void twentyHoursPast_dataNotExpired_shouldNotThrowOrderExpiredExecption() {
        Instant now = Instant.now();
        Instant later = Instant.now().plus(Duration.ofHours(20));
        when(clock.instant()).thenReturn(now,later);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }

    @Test void twentyFourHoursPast_dataNotExpired_shouldNotThrowOrderExpiredExecption() {
        Instant now = Instant.now();
        Instant later = Instant.now().plus(Duration.ofHours(24));
        when(clock.instant()).thenReturn(now,later);
        order.submit();
        assertDoesNotThrow(order::confirm);
    }
}
