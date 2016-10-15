package com.company.miniq.broker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessagesStorage<E> implements Store<E> {

    private ConcurrentLinkedQueue<E> store = new ConcurrentLinkedQueue<E>();

    @Override
    public void add(E element) {
        store.add(element);
    }

    @Override
    public List<E> get(int count) {
        List<E> elements = new ArrayList<E>(store.size());
        while(!store.isEmpty() && count > 0) {
            E e = store.poll();
            if(e != null) {
                elements.add(e);
            }

            count--;
        }

        return elements;
    }

    @Override
    public List<E> getAll() {
        List<E> envelopes = new ArrayList<E>(store.size());
        while(!store.isEmpty()) {
            E e = store.poll();
            if(e != null) {
                envelopes.add(e);
            }
        }

        return envelopes;
    }
}
