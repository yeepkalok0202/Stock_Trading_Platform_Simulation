package com.example.demo;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ClockFunction implements Serializable{
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // default constructor
    public ClockFunction() {
    }

    // for difference usage as the difference can only accept raw type
    public static LocalDateTime getRawCurrentTime() {
        LocalDateTime time = LocalDateTime.now();
        return time;
    }

    // for display purpose
    public static String getStringCurrentTime() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(formatter);
    }
    //for ROR display purpose
    public static String getStringCurrentTimeWithoutTime() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(formatter2);
    }

    // find the differeence and display to String
    public static String getDifferenceBetweenTwoTime(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        double seconds = duration.toMillis() / 1000.0;
        return String.format("%dD %dH %dM %.3fS%n", days, hours, minutes, seconds);
    }
}
