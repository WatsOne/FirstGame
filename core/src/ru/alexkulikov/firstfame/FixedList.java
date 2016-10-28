package ru.alexkulikov.firstfame;

import com.badlogic.gdx.utils.Array;

public class FixedList<T> extends Array<T> {

    public FixedList(int capacity, Class<T> type) {
        super(false, capacity, type);
    }

    public void insert(T t) {
        T[] items = this.items;
        size = Math.min(size + 1, items.length);
        System.arraycopy(items, 0, items, 1, size - 1);
        items[0] = t;
    }
}