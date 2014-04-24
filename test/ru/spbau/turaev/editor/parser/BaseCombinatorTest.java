package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;

import java.util.Collection;

public class BaseCombinatorTest {
    protected <T> Pair<T, String> getFirst(Collection<Pair<T, String>> collection) {
        for (Pair<T, String> pair : collection) {
            return pair;
        }
        return null;
    }
}
