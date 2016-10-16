package com.company.miniq.message;

/*
    InTransitEnvelop are used to store unacknowledged Envelops with expiry time.
 */
public class InTransitEnvelop implements Comparable<InTransitEnvelop>{
    private final long expiryTime;
    private final Envelope envelope;

    public InTransitEnvelop(long expiryTime, Envelope envelope) {
        this.expiryTime = expiryTime;
        this.envelope = envelope;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    @Override
    public int compareTo(InTransitEnvelop obj) {
        if (this.expiryTime == obj.getExpiryTime()) {
            return 0;
        } else if (this.expiryTime > obj.getExpiryTime()) {
            return 1 ;
        } else {
            return -1;
        }
    }

}
