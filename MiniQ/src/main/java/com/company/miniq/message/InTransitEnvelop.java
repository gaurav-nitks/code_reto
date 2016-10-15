package com.company.miniq.message;

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
    public int compareTo(InTransitEnvelop o) {
        if (this.expiryTime == o.getExpiryTime()) {
            return 0;
        } else if (this.expiryTime > o.getExpiryTime()) {
            return 1 ;
        } else {
            return -1;
        }
    }

}
