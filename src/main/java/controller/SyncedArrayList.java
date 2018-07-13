package controller;

import java.util.ArrayList;

public class SyncedArrayList<E> extends ArrayList<E> {
    @Override
    public boolean add(E e) {
        synchronized (this) {
            return super.add(e);
        }
    }
}
