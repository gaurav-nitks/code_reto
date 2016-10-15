package com.company.miniq.broker;

import com.company.miniq.message.Envelope;
import com.company.miniq.message.Message;
import com.company.miniq.message.MessageId;

import java.util.List;

public interface Broker {
    MessageId publish(Message message);

    List<Envelope> consume();

    List<Envelope> consume(int count); // returns at most count number of messages.

    void acknowledge(MessageId messageId);

    void close(); // cleanup resources and stop scheduler.
}
