package ru.spbau.turaev.editor.common;

public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s;%s)", first.toString(), second.toString());
    }
}
