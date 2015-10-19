package tk.lenkyun.foodbook.foodbook.Domain.Operation;

import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

/**
 * Created by lenkyun on 19/10/2558.
 */
public interface Bundle<E> extends FoodbookType, Iterable<E> {
    E get(int index);

    void put(E content);

    void remove(int index);

    int size();
}
