package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class FakeClock extends Clock {

    public FakeClock() {
        
    }

    @Override public ZoneId getZone() {
        return null;
    }

    @Override public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override public Instant instant() {
        return null;
    }
}
