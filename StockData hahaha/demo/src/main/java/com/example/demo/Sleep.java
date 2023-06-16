package com.example.demo;

public class Sleep {
    public static void sleepInSeconds(int seconds) {
        try {
            // Convert seconds to milliseconds
            long milliseconds = seconds * 1000;
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
