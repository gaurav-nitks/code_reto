package com.company.miniq.scheduler;

import com.company.miniq.broker.storage.Store;
import com.company.miniq.broker.storage.TransitStorage;
import com.company.miniq.message.Envelope;
import com.company.miniq.message.InTransitEnvelop;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
    Scheduler is responsible for checking TransitStorage and remove expired Envelopes and
    push them back to store so that they are available for consumption.

    It runs every second, as acknowledge wait timeout is configured in seconds.
 */
public class Scheduler extends TimerTask {
    private static final int FREQ = 1000; // 1 sec

    private final Store<Envelope> messageStorage;
    private final TransitStorage inTransitMessageStore;

    private Timer time = new Timer();

    public Scheduler(Store<Envelope> messageStorage, TransitStorage inTransitMessageStore) {
        this.messageStorage = messageStorage;
        this.inTransitMessageStore = inTransitMessageStore;
    }

    public void start() {
        time.schedule(this, 0, FREQ);
    }

    public void stop() {
        time.cancel();
    }

    @Override
    public void run() {
        List<InTransitEnvelop> timedOutMessages =
                inTransitMessageStore.getElementInsertedBefore(System.currentTimeMillis());

        for(InTransitEnvelop message : timedOutMessages) {
            message.getEnvelope().setRedelivered(true);
            messageStorage.add(message.getEnvelope());
        }
    }

}
