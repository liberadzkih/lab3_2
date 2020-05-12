package edu.iis.mto.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    @Test
    void shouldThrowExceptionWhenDateExpired() {
        Order order = expiredSubmittedOrder();
        assertThrows(OrderExpiredException.class, order::confirm);
    }

    @Test
    void shouldConfirmTheLastHour() {
        Order order = submittedOrderInLastHour();
        assertDoesNotThrow(order::confirm);
    }

    @Test
    void shouldConfirm() {
        Order order = submittedOrderWithDateBeforeExpiredDate();
        assertDoesNotThrow(order::confirm);
    }

    Order expiredSubmittedOrder(){
        return orderWithTime(25);
    }

    Order submittedOrderInLastHour(){
        return orderWithTime(24);
    }

    Order submittedOrderWithDateBeforeExpiredDate(){
        return orderWithTime(20);
    }

    Order orderWithTime(int hours){
        Clock clockMock = mock(Clock.class);
        Order order = new Order(clockMock);
        Instant now = Instant.now();
        Instant afterPeriod = Instant.now().plus(Duration.ofHours(hours));
        when(clockMock.instant())
                .thenReturn(now, afterPeriod);
        order.addItem(new OrderItem());
        order.submit();
        return order;
    }
}
