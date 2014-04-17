package ru.spbau.turaev.editor;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionExtentions {
    public static <T> Collection<T> concat(T head, Collection<T> tail) {
        Collection<T> result = new ArrayList<>();
        result.add(head);
        result.addAll(tail);
        return result;
    }

    public static <T> Collection<T> concat(Collection<T> head, Collection<T> tail) {
        Collection<T> result = new ArrayList<>();
        result.addAll(head);
        result.addAll(tail);
        return result;
    }

    public static <T> void PrintCollection(Collection<T> collection) {
        System.out.print("[");
        for (T item : collection) {
            System.out.print(item.toString());
        }
        System.out.println("]");
    }
}
