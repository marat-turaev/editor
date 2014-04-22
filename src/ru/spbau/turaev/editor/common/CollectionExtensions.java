package ru.spbau.turaev.editor.common;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionExtensions {
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

    public static String ConvertToString(Collection<Character> collection) {
        char[] tmp = new char[collection.size()];
        int i = 0;
        for (Character c : collection) {
            tmp[i++] = c;
        }
        return new String(tmp);
    }
}
