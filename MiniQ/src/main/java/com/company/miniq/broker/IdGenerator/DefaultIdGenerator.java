package com.company.miniq.broker.IdGenerator;

import com.company.miniq.message.MessageId;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultIdGenerator implements IdGenerator {

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public MessageId generateNextId() {
        return new MessageId(counter.incrementAndGet());
    }
}
