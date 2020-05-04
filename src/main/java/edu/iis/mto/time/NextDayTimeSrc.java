package edu.iis.mto.time;

public class NextDayTimeSrc implements TimeSource {
    private static final long ONE_HOUR = 60*60*1000;
    private static final long ONE_DAY = 24*ONE_HOUR;

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis() + ONE_DAY + ONE_HOUR;
    }
}

