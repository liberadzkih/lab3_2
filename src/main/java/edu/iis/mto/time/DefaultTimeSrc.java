package edu.iis.mto.time;

public class DefaultTimeSrc implements TimeSource {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}

