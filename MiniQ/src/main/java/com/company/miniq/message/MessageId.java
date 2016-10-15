package com.company.miniq.message;

public class MessageId {
    private final long id;

    public MessageId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
