package com.company.miniq.broker;

import com.company.miniq.broker.IdGenerator.DefaultIdGenerator;
import com.company.miniq.broker.IdGenerator.IdGenerator;
import com.company.miniq.broker.config.Configuration;
import com.company.miniq.broker.storage.MessagesStorage;
import com.company.miniq.broker.storage.Store;
import com.company.miniq.broker.storage.TransitMessagesStore;
import com.company.miniq.broker.storage.TransitStorage;
import com.company.miniq.message.Envelope;
import com.company.miniq.message.InTransitEnvelop;
import com.company.miniq.message.Message;
import com.company.miniq.message.MessageId;
import com.company.miniq.scheduler.Scheduler;

import java.util.List;

public class DefaultMiniQ implements MiniQ {
    private final Configuration config;
    private IdGenerator idGenerator;
    private final Store<Envelope> messageStorage;
    private final TransitStorage inTransitMessageStore;

    private final Scheduler scheduler;

    public DefaultMiniQ(Configuration config,
                        IdGenerator idGenerator,
                        Store<Envelope> messageStorage,
                        TransitStorage inTransitMessageStore) {
        this.config = config;
        this.idGenerator = idGenerator;
        this.messageStorage = messageStorage;
        this.inTransitMessageStore = inTransitMessageStore;
        this.scheduler = new Scheduler(messageStorage, inTransitMessageStore);

        scheduler.start();
    }

    public DefaultMiniQ(Configuration config) {
        this(
            config,
            new DefaultIdGenerator(),
            new MessagesStorage<Envelope>(),
            new TransitMessagesStore());
    }

    @Override
    public MessageId publish(Message message) {
        MessageId id = idGenerator.generateNextId();
        messageStorage.add(new Envelope(id, message));
        return id;
    }

    @Override
    public List<Envelope> consume() {
        List<Envelope> messages = messageStorage.getAll();
        addToInTransit(messages);
        return messages;
    }

    @Override
    public List<Envelope> consume(int count) {
        List<Envelope> messages = null;

        if(count > 0) {
            messages = messageStorage.get(count);
            addToInTransit(messages);
        }

        return messages;
    }

    @Override
    public void acknowledge(MessageId messageId) {
        inTransitMessageStore.remove(messageId);
    }

    @Override
    public void close() {
        scheduler.stop();
    }

    private void addToInTransit(List<Envelope> messages){
        long expiryTime = System.currentTimeMillis() + (config.getAcknowledgeWaitTimeOut() * 1000) ;

        for(Envelope message: messages) {
            inTransitMessageStore.add(new InTransitEnvelop(expiryTime, message));
        }
    }
}
