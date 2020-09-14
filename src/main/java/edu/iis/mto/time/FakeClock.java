package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FakeClock extends Clock {

    private final ZoneId DEFAULT_TZONE = ZoneId.systemDefault();
    private Instant instant;
    private int hours;

    public FakeClock(int hours) {
        this.hours = hours;
        this.instant = Instant.now();
    }

    @Override public ZoneId getZone() {
        return DEFAULT_TZONE;
    }

    @Override public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override public Instant instant() {
        instant = instant.plus(hours, ChronoUnit.HOURS);
        return instant;
    }
}
