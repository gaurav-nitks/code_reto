package com.company.miniq.broker.storage;

import com.company.miniq.message.InTransitEnvelop;
import com.company.miniq.message.MessageId;

import java.util.List;

public interface TransitStorage {
    void add(InTransitEnvelop inTransitEnvelop);
    List<InTransitEnvelop> getElementInsertedBefore(long timestamp);
    void remove(MessageId messageId);
}
