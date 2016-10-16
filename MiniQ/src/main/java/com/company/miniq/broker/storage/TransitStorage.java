package com.company.miniq.broker.storage;

import com.company.miniq.message.InTransitEnvelop;
import com.company.miniq.message.MessageId;

import java.util.List;

/*
    TransitStorage stores messages which are delivered to subscribers but not yet acknowledged.
    Envelops are wrapped in InTransitEnvelop which contains expiry time of message to be available
    for re consumption.
 */
public interface TransitStorage {
    void add(InTransitEnvelop inTransitEnvelop);
    List<InTransitEnvelop> getElementInsertedBefore(long timestamp);
    void remove(MessageId messageId);
}
