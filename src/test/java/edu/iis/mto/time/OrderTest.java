package edu.iis.mto.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order = new Order();

    @Test()
    public void throws_exception_when_order_is_expired() {
        //given
        order.submit();
        order.setTimeSource(new NextDayTimeSrc());  //zmiana na czas po 25h (limit 24 przekroczony)

        //when-than
        Assertions.assertThrows(OrderExpiredException.class, () -> order.confirm());
    }
}
