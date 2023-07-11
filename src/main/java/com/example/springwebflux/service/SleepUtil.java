package com.example.springwebflux.service;

public class SleepUtil {


    public static void sleepSeconds(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
