package com.company.miniq.scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private final TimerTask broker;
    Timer time = new Timer();

    public Scheduler(TimerTask broker) {
        this.broker = broker;
    }

    public void start() {
        time.schedule(broker, 0, 1000); // Create Repetitively task for every 1 secs
    }

    public void stop() {
        time.cancel();
    }
}
