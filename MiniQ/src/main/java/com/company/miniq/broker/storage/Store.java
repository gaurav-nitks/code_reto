package com.company.miniq.broker.storage;

import java.util.List;

/*
    Abstract store for storing messages available for consumption.
    MiniQ will be interacting with this for persistence of message.
    This can later be a proxy for scaled message store.
 */
public interface Store<E> {
    void add(E element);
    List<E> get(int count);
    List<E> getAll();
}
