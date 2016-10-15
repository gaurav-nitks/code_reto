package com.company.miniq.broker.IdGenerator;

import com.company.miniq.message.MessageId;

public interface IdGenerator {
    MessageId generateNextId();
}
