package edu.iis.mto.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static final int VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private Clock clock;
    private Instant subbmitionDate;

    public Order() {
        this.clock = Clock.systemDefaultZone();
        this.orderState = State.CREATED;
    }

    public Order(Clock clock) {
        this.clock = clock;
        this.orderState = State.CREATED;
    }

    public void addItem(OrderItem item) {
        requireState(State.CREATED, State.SUBMITTED);

        items.add(item);
        orderState = State.CREATED;

    }

    public void submit() {
        requireState(State.CREATED);

        orderState = State.SUBMITTED;
        subbmitionDate = clock.instant();

    }

    public void confirm() {
        requireState(State.SUBMITTED);
        int hoursElapsedAfterSubmission = (int) Duration.between(subbmitionDate, clock.instant()).toHours();
        if (hoursElapsedAfterSubmission > VALID_PERIOD_HOURS) {
            orderState = State.CANCELLED;
            throw new OrderExpiredException();
        } else {
            orderState = State.CONFIRMED;
        }
    }

    public void realize() {
        requireState(State.CONFIRMED);
        orderState = State.REALIZED;
    }

    State getOrderState() {
        return orderState;
    }

    private void requireState(State... allowedStates) {
        for (State allowedState : allowedStates) {
            if (orderState == allowedState) {
                return;
            }
        }

        throw new OrderStateException(
                "order should be in state " + allowedStates + " to perform required  operation, but is in " + orderState);

    }

    public enum State {
        CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
    }
}
