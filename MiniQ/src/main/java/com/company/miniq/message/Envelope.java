package com.company.miniq.message;

public class Envelope{
    private final MessageId id;
    private final Message message;
    private final long insertTime;

    public void setRedelivered(boolean isRedelivered) {
        this.isRedelivered = isRedelivered;
    }

    private boolean isRedelivered = false;

    public Envelope(MessageId id, Message message, long insertTime) {
        this.id = id;
        this.message = message;
        this.insertTime = insertTime;
    }

    public Envelope(MessageId id, Message message) {
        this(id, message, System.currentTimeMillis());
    }

    public boolean isRedelivered() {
        return isRedelivered;
    }

    public Message getMessage() {
        return message;
    }

    public MessageId getId() {
        return id;
    }

    public long getInsertTime() {
        return insertTime;
    }
}
