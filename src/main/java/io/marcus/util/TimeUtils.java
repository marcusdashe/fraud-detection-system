package io.marcus.util;

public class TimeUtils {
    public static final long FIVE_MINUTES_IN_SECONDS = 300;
    public static final long TEN_MINUTES_IN_SECONDS = 600;
    public static final long TWENTY_FOUR_HOURS_IN_SECONDS = 86400;


    /**
     * Checks if two events are within the specified time window.
     *
     * @param timestamp1 The timestamp of the first event.
     * @param timestamp2 The timestamp of the second event.
     * @param windowInSeconds The time window in seconds.
     * @return True if the events are within the time window, false otherwise.
     */
    public static boolean areEventsWithinTimeWindow(long timestamp1, long timestamp2, long windowInSeconds) {
        return (timestamp1 - timestamp2) <= windowInSeconds;
    }

    /**
     * Gets the current timestamp in seconds.
     *
     * @return The current timestamp in seconds.
     */
    public static long getCurrentTimestampInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}

