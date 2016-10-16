package com.company.miniq.broker.storage;

import com.company.miniq.message.InTransitEnvelop;
import com.company.miniq.message.MessageId;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class TransitMessagesStore implements TransitStorage {

    PriorityQueue<InTransitEnvelop> store = new PriorityQueue<InTransitEnvelop>();
    private ConcurrentHashMap<MessageId, InTransitEnvelop> messageIdToEnvelopMap = new ConcurrentHashMap<MessageId, InTransitEnvelop>();

    @Override
    public void add(InTransitEnvelop element) {
        store.add(element);
        messageIdToEnvelopMap.put(element.getEnvelope().getId(), element);
    }

    @Override
    public List<InTransitEnvelop> getElementInsertedBefore(long time) {
        List<InTransitEnvelop> timedoutEnvelopes = new ArrayList<InTransitEnvelop>();
        while(store.peek() != null && store.peek().getExpiryTime() <= time) {
            InTransitEnvelop expired = store.poll();
            timedoutEnvelopes.add(expired);
            messageIdToEnvelopMap.remove(expired.getEnvelope().getId());
        }

        return timedoutEnvelopes;
    }

    @Override
    public void remove(MessageId messageId) {
        InTransitEnvelop removedMessage = messageIdToEnvelopMap.remove(messageId);
        if(removedMessage != null) {
            store.remove(removedMessage);
        }
    }
}
