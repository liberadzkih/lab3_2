package edu.iis.mto.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    @Test
    public void throws_exception_when_order_in_wrong_state() {
        Assertions.assertThrows(OrderStateException.class, () -> order.confirm());
    }

    @Test
    public void confirm_should_check_time_from_TimeSource() {
        TimeSource timeSourceMock = mock(TimeSource.class);
        order.submit();

        order.setTimeSource(timeSourceMock);
        order.confirm();

        verify(timeSourceMock).currentTimeMillis();
    }

    @Test
    public void confirm_should_check_time_from_TimeSource() {
        TimeSource timeSourceMock = mock(TimeSource.class);
        order.submit();

        order.setTimeSource(timeSourceMock);
        order.confirm();

        verify(timeSourceMock).currentTimeMillis();
    }


}
