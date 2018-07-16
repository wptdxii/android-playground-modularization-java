package com.wptdxii.framekit.util;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.exception.InstantiationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Collections {

    private Collections() {
        throw new InstantiationException();
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> List<E> newArrayList(@NonNull Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        addAll(list, elements);
        return list;
    }

    public static <E> List<E> newArrayList(@NonNull Iterable<E> elements) {
        return (elements instanceof Collection) ?
                new ArrayList<>(cast(elements)) : newArrayList(elements.iterator());
    }


    public static <E> void addAll(Collection<E> collection, Iterator<? extends E> iterator) {
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
    }

    public static <E> Collection<E> cast(@NonNull Iterable<E> iterable) {
        return (Collection<E>) iterable;
    }
}
