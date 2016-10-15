package com.company.miniq.broker.storage;

import java.util.List;

public interface Store<E> {
    void add(E element);
    List<E> get(int count);
    List<E> getAll();
}
