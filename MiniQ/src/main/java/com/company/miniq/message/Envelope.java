package com.company.miniq.message;

/*
    Envelope is metadata + actual message (sent by publisher).
    Subscribers will receive Envelopes when they consume.
 */
public class Envelope{
    private final MessageId id;
    private final Message message;
    private final long createTime;

    public void setRedelivered(boolean isRedelivered) {
        this.isRedelivered = isRedelivered;
    }

    private boolean isRedelivered = false;

    public Envelope(MessageId id, Message message, long createTime) {
        this.id = id;
        this.message = message;
        this.createTime = createTime;
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

    public long getCreateTime() {
        return createTime;
    }
}
