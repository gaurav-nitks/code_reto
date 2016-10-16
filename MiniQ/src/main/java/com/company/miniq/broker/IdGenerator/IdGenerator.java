package com.company.miniq.broker.IdGenerator;

import com.company.miniq.message.MessageId;

/*
    IdGenerator is for producing sequential Id for incoming messages.
 */

public interface IdGenerator {
    MessageId generateNextId();
}
