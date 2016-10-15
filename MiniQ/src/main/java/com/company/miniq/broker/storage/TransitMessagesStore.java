package com.company.miniq.broker.storage;

import com.company.miniq.message.Envelope;
import com.company.miniq.message.InTransitEnvelop;
import com.company.miniq.message.Message;
import com.company.miniq.message.MessageId;

import java.util.*;
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

    public static void main(String[] args) {
        TransitMessagesStore s = new TransitMessagesStore();

        s.add(new InTransitEnvelop(1, new Envelope(new MessageId(1), new Message("hello".getBytes()))));
        s.add(new InTransitEnvelop(2, new Envelope(new MessageId(2), new Message("hello".getBytes()))));
        s.add(new InTransitEnvelop(3, new Envelope(new MessageId(3), new Message("hello".getBytes()))));
        s.add(new InTransitEnvelop(1, new Envelope(new MessageId(4), new Message("hello".getBytes()))));
        s.add(new InTransitEnvelop(2, new Envelope(new MessageId(5), new Message("hello".getBytes()))));
        s.add(new InTransitEnvelop(2, new Envelope(new MessageId(6), new Message("hello".getBytes()))));

        List l = s.getElementInsertedBefore(1);

        System.out.println("Done"+l);
    }
}
