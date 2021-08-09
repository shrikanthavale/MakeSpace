package com.shrikane.makespace.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static LocalDateTime toLocalDate(final String timeInFormat) {
        return LocalDate.now().atTime(LocalTime.parse(timeInFormat));
    }
}
