package com.zenpanda.statsservice.util;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimeUtil {

    /**
     * Gets time in epoch in millis in UTC time zone.
     * @return
     */
    public long currentTimestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * Converts given milliseconds to seconds.
     * @param milliseconds
     * @return
     */
    public long convertMillisecondsToSeconds(long milliseconds) {
        return milliseconds / 1000;
    }
}
